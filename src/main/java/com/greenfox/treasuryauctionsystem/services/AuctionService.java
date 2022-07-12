package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.TempSecurityDTO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface AuctionService {

  Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus();

  void disable(Long id)throws NoSuchAuctionException;

  void create(Auction auction);

  Map<String, String> validateSecurityForAuction(Auction auction, TempSecurityDTO tempSecurityDTO);

  Auction setDateToAuction(Auction auction, AuctionDateDTO auctionDateDTO);

  // find auction by id
  Auction findById(Long auction_id);

  void process(Long id) throws NoSuchAuctionException;

}
