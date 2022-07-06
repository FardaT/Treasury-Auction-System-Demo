package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Bid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BidService {

    // READ
    List<Bid> getAllBids();

    // STORE
    Map<String, String> saveBid(Bid bid);

    // DESTROY
    void disableBid(Long bidId);
}
