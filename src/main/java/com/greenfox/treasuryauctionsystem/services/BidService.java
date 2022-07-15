package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.BidResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BidService {

	// READ
	List<Bid> getAllBids ();

	// READ
	List<BidResponseDTO> getAllBidsDto (String userName);

    // STORE
    Map<String, String> saveBid(List<BidDTO> bidDTOS, AppUser appUser, Auction auction);

	// DESTROY
	void disableBid (Long bidId);
}
