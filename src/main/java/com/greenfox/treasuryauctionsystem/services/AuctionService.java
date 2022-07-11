package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface AuctionService {

  Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus() throws NullPointerException;

  void disable(Long id)throws NoSuchAuctionException;

  // find auction by id
  Auction findById(Long auction_id);

  void process(Long id) throws NoSuchAuctionException;
}
