package com.greenfox.treasuryauctionsystem.services;


import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service

public interface AuctionService {

  Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus() throws NullPointerException;

  void disable(Long id);

  void process(Long id);

  void create(Auction auction);

  Auction addSecurityToAuction(Auction auction, TreasurySecurity treasurySecurity);

  Auction setDateToAuction(Auction auction, AuctionDateDTO auctionDateDTO);
}
