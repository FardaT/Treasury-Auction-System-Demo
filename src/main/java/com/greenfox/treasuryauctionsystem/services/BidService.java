package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    // READ
    List<Bid> getAllBids();

    // STORE
    void saveBid(Bid bid);

    // DESTROY
    void disableBid(Long bidId);
}
