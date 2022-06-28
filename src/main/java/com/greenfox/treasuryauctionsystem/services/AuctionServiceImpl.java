package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AuctionServiceImpl implements AuctionService {


  private AuctionRepository auctionRepository;

  @Autowired
  public AuctionServiceImpl(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  public Map<String, List<Auction>> getAllAuctionsBy() throws NullPointerException {

    Map<String, List<Auction>> auctionsMap = new HashMap<>();
    auctionsMap.put("finished", new ArrayList<>());
    auctionsMap.put("upcoming", new ArrayList<>());
    auctionsMap.put("ongoing", new ArrayList<>());

    List<Auction> allAuctions = auctionRepository.findAll();

    if (allAuctions == null) {
      throw new NullPointerException();
    }
    for (Auction auction : allAuctions) {
      if (auction.getAuctionEndDate().isBefore(LocalDateTime.now())) {
        List<Auction> finishedList = auctionsMap.get("finished");
        finishedList.add(auction);
        auctionsMap.put("finished", finishedList);
      } else if (auction.getAuctionStartDate().isAfter(LocalDateTime.now())) {
        List<Auction> upcomingList = auctionsMap.get("upcoming");
        upcomingList.add(auction);
        auctionsMap.put("upcoming", upcomingList);
      } else {
        List<Auction> ongoingList = auctionsMap.get("ongoing");
        ongoingList.add(auction);
        auctionsMap.put("ongoing", ongoingList);
      }
    }
    return auctionsMap;
  }
}
