package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Bid;
import org.springframework.stereotype.Service;

@Service
public interface BidService {
    void saveBid(Bid bid);
}
