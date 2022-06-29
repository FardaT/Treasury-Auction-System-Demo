package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Auction;
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
    if(auction.isPresent()) {
      Auction currentAuction = auction.get();
      currentAuction.setDisabled(true);
      auctionRepository.save(currentAuction);
    }
  }
}
