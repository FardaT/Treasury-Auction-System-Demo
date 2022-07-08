package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AuctionServiceImpl implements AuctionService {


  private final AuctionRepository auctionRepository;
  private final TreasurySecurityRepository treasurySecurityRepository;

  private final BidRepository bidRepository;

  @Autowired
  public AuctionServiceImpl(AuctionRepository auctionRepository,
                            TreasurySecurityRepository treasurySecurityRepository,
                            BidRepository bidRepository) {
    this.auctionRepository = auctionRepository;
    this.treasurySecurityRepository = treasurySecurityRepository;
    this.bidRepository = bidRepository;
  }

  @Override
  public Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus()
      throws NullPointerException {

    Map<String, List<AuctionResponseDTO>> auctionsMap = new HashMap<>();
    auctionsMap.put("finished", null);
    auctionsMap.put("upcoming", null);
    auctionsMap.put("ongoing", null);

    List<Auction> allAuctions = auctionRepository.findAll();

    if (allAuctions.size() == 0) {
      throw new NullPointerException();
    }
    for (Auction auction : allAuctions) {
      if (!auction.isDisabled()) {
        if (auction.getAuctionEndDate().isBefore(LocalDateTime.now())) {
          if (auctionsMap.get("finished") == null) {
            auctionsMap.put("finished", new ArrayList<>());
          }
          List<AuctionResponseDTO> finishedList = auctionsMap.get("finished");
          finishedList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("finished", finishedList);
        } else if (auction.getAuctionStartDate().isAfter(LocalDateTime.now())) {
          if (auctionsMap.get("upcoming") == null) {
            auctionsMap.put("upcoming", new ArrayList<>());
          }
          List<AuctionResponseDTO> upcomingList = auctionsMap.get("upcoming");
          upcomingList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("upcoming", upcomingList);
        } else {
          if (auctionsMap.get("ongoing") == null) {
            auctionsMap.put("ongoing", new ArrayList<>());
          }
          List<AuctionResponseDTO> ongoingList = auctionsMap.get("ongoing");
          ongoingList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("ongoing", ongoingList);
        }
      }
    }
    return auctionsMap;
  }

  @Override
  public void disable(Long id) throws NoSuchAuctionException {
    Optional<Auction> auction = auctionRepository.findById(id);
    //todo check if it is upcoming auction
    if (auction.isPresent()) {
      Auction currentAuction = auction.get();
      if (currentAuction.getAuctionStartDate().isBefore(LocalDateTime.now())) {
        throw new NoSuchAuctionException("No upcoming auctions in the database");
      }
      currentAuction.setDisabled(true);
      auctionRepository.save(currentAuction);
    }
  }

  @Override
  @Transactional
  public void process(Long id) throws NoSuchAuctionException {

    //get auction by id
    Optional<Auction> auctionOptional = auctionRepository.findById(id);
    Auction currentAuction;
    if (auctionOptional.isEmpty()) {
      throw new NoSuchAuctionException("No auction found with the provided Id.");
    } else {
      currentAuction = auctionOptional.get();
    }

    //get securities of the auction
    List<TreasurySecurity> treasurySecurityList = currentAuction.getTreasurySecurityList();

    //get total amount of the securities
    Map<Long, Long> totalAmountsBySecurities = new HashMap<>();
    for (TreasurySecurity treasurySecurity : treasurySecurityList) {
      totalAmountsBySecurities.put(treasurySecurity.getId(), treasurySecurity.getTotalAmount());
    }

    //map bids to treasurySecurity ids
    Map<Long, List<Bid>> bidListMap = new HashMap<>();
    for (TreasurySecurity treasurySecurity : treasurySecurityList) {
      List<Bid> bidList = treasurySecurity.getBidList();
      bidListMap.put(treasurySecurity.getId(), bidList);
    }

    // split to competitive and non-competitive maps
    Map<Long, List<Bid>> competitiveBidListMap = new HashMap<>();
    Map<Long, List<Bid>> nonCompetitiveBidListMap = new HashMap<>();

    for (Map.Entry<Long, List<Bid>> entry : bidListMap.entrySet()) {
      for (Bid bid : entry.getValue()) {
        if (bid.isCompetitive()) {
          if (!competitiveBidListMap.containsKey(bid.getTreasurySecurity().getId())) {
            competitiveBidListMap.put(bid.getTreasurySecurity().getId(), new ArrayList<>());
          }
          competitiveBidListMap.get(entry.getKey()).add(bid);
        } else {
          if (!nonCompetitiveBidListMap.containsKey(bid.getTreasurySecurity().getId())) {
            nonCompetitiveBidListMap.put(bid.getTreasurySecurity().getId(), new ArrayList<>());
          }
          nonCompetitiveBidListMap.get(entry.getKey()).add(bid);
        }
      }
    }

    //counts non-competitive offer amounts
    Map<Long, Long> totalNonCompetitiveBidAmount = new HashMap<>();

    for (Map.Entry<Long, List<Bid>> entry : nonCompetitiveBidListMap.entrySet()) {
      for (Bid bid : entry.getValue()) {
        if (!totalNonCompetitiveBidAmount.containsKey(bid.getTreasurySecurity().getId())) {
          totalNonCompetitiveBidAmount.put(bid.getTreasurySecurity().getId(), 0L);
        }
        Long currentAmount = bid.getAmount();
        totalNonCompetitiveBidAmount.put(entry.getKey(),
            totalNonCompetitiveBidAmount.get(entry.getKey()) + currentAmount);
      }
    }

    //sorts the lists of the competitiveBidListMap
    for (Map.Entry<Long, List<Bid>> entry : competitiveBidListMap.entrySet()) {
      List<Bid> currentBidList = entry.getValue();
      Collections.sort(currentBidList);
    }

    //calculate security's high rate, set bid's isAccepted & acceptedValue
    Map<Long, Float> highRateMap =
        getHighRateMap(totalAmountsBySecurities, competitiveBidListMap,
            totalNonCompetitiveBidAmount);

    //set all noncompetitive auctions setAcceptedValue and setAcceptedFlag
    for (Map.Entry<Long, List<Bid>> entry : nonCompetitiveBidListMap.entrySet()) {
      for (Bid bid : entry.getValue()) {
        bid.setAcceptedValue(bid.getAmount());
        bid.setAccepted(true);
        bidRepository.save(bid);
      }
    }

    //set highRate of all treasurySecurities
    for (TreasurySecurity treasurySecurity : treasurySecurityList) {
      if (highRateMap.containsKey(treasurySecurity.getId())) {
        float finalHighRate = highRateMap.get(treasurySecurity.getId());
        treasurySecurity.setHighRate(finalHighRate);
        treasurySecurityRepository.save(treasurySecurity);
      }
    }

    //sets auction's isProcessed flag
    currentAuction.setProcessed(true);
    auctionRepository.save(currentAuction);
  }

  private Map<Long, Float> getHighRateMap(Map<Long, Long> totalAmountsBySecurities,
                                          Map<Long, List<Bid>> competitiveBidListMap,
                                          Map<Long, Long> totalNonCompetitiveBidAmount) {
    Map<Long, Float> highRateMap = new HashMap<>();

    for (Map.Entry<Long, List<Bid>> entry : competitiveBidListMap.entrySet()) {

      //get security total amount
      long totalAmountOfCurrentSecurity = totalAmountsBySecurities.get(entry.getKey());

      //get remaining total amount after non-competitive bids deducted
      long remainingTotalAmountAfterNonCompetitiveBidsDeducted = totalAmountOfCurrentSecurity;
      if (totalNonCompetitiveBidAmount.containsKey(entry.getKey())) {
        remainingTotalAmountAfterNonCompetitiveBidsDeducted =
            totalAmountOfCurrentSecurity - totalNonCompetitiveBidAmount.get(entry.getKey());
      }

      boolean isExceeded = false;

      //check high rate & save bids & treasurySecurity & auction
      for (Bid bid : entry.getValue()) {
        if (remainingTotalAmountAfterNonCompetitiveBidsDeducted - bid.getAmount() > 0) {
          remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
          bid.setAccepted(true);
          bid.setAcceptedValue(bid.getAmount());
          highRateMap.put(entry.getKey(), bid.getRate());
        } else if (remainingTotalAmountAfterNonCompetitiveBidsDeducted - bid.getAmount() == 0) {
          remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
          bid.setAccepted(true);
          bid.setAcceptedValue(bid.getAmount());
          highRateMap.put(entry.getKey(), bid.getRate());
          isExceeded = true;
        } else {
          remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
          if (!isExceeded) {
            bid.setAccepted(true);
            highRateMap.put(entry.getKey(), bid.getRate());
            long currentAcceptedValue =
                bid.getAmount() + remainingTotalAmountAfterNonCompetitiveBidsDeducted;
            bid.setAcceptedValue(currentAcceptedValue);
            isExceeded = true;
          } else {
            bid.setAccepted(false);
            bid.setAcceptedValue(0);
          }
        }
        bidRepository.save(bid);
      }
    }
    return highRateMap;
  }
}
