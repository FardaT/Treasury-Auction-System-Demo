package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.InvalidAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AuctionServiceImpl implements AuctionService {


  private AuctionRepository auctionRepository;

  @Autowired
  public AuctionServiceImpl(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  @Override
  public Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus()
      throws NullPointerException {

    Map<String, List<AuctionResponseDTO>> auctionsMap = new HashMap<>();
    auctionsMap.put("finished", null);
    auctionsMap.put("upcoming", null);
    auctionsMap.put("ongoing", null);

    List<Auction> allAuctions = auctionRepository.findAll();

    if (allAuctions == null) {
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
  public void disable(Long id) {
    Optional<Auction> auction = auctionRepository.findById(id);
    if (auction.isPresent()) {
      Auction currentAuction = auction.get();
      currentAuction.setDisabled(true);
      auctionRepository.save(currentAuction);
    }
  }

  @Override
  public void process(Long id) {
    // TODO: 2022. 06. 30. function must be coded
    Optional<Auction> auction = auctionRepository.findById(id);
    if (auction.isPresent() && auction.get().getAuctionEndDate().isBefore(LocalDateTime.now())) {
      Auction currentAuction = auction.get();
      currentAuction.setProcessed(true);
      auctionRepository.save(currentAuction);
    }
  }
  @Override
  public void create(Auction auction) {
    if (auction.getTreasurySecurityList().isEmpty()){
      throw new InvalidAuctionException("Auction must contain security");
    }
    auctionRepository.save(auction);
  }

  @Override
  public Auction addSecurityToAuction(Auction auction, TreasurySecurity treasurySecurity) {
    //TODO set further validations for securities
    List<TreasurySecurity> securityList = auction.getTreasurySecurityList();
    for (TreasurySecurity ts : securityList) {
      if (ts.getSecurityName().equals(treasurySecurity.getSecurityName())){
        throw new InvalidAuctionException("One Auction cannot contain identical types of securities");
      }
    }
    securityList.add(treasurySecurity);
    auction.setTreasurySecurityList(securityList);
    return auction;
  }
  @Override
  public Auction setDateToAuction(Auction auction, AuctionDateDTO auctionDateDTO) {
    if(auctionDateDTO.getAuctionStartDate().isBefore(LocalDateTime.now())){
      throw new InvalidAuctionException("Auction start date out of bound");
    }
    if(auctionDateDTO.getAuctionEndDate().isBefore(LocalDateTime.now())){
      throw new InvalidAuctionException("Auction end date out of bound");
    }
    if(auctionDateDTO.getAuctionEndDate().isBefore(auctionDateDTO.getAuctionStartDate())){
      throw new InvalidAuctionException("Invalid auction dates");
    }
    auction.setAuctionStartDate(auctionDateDTO.getAuctionStartDate());
    auction.setAuctionEndDate(auctionDateDTO.getAuctionEndDate());
    return auction;
  }
}
