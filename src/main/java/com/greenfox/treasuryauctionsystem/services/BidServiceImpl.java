package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BidServiceImpl implements BidService {

    // DI
    private final BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    // READ
    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    // STORE
    @Override
    public Map<String, String> saveBid(Bid bid) {

        Map<String, String> errors = new HashMap<>();

        // VALIDATIONS
        if (bid.getAmount() <= 0) {
            errors.put("AMOUNT_POSITIVE", "Amount has to be a positive number!");
        }
        if (bid.getAmount() % ApplicationDetails.multiple_of != 0) {
            errors.put("AMOUNT_HUNDRED", "Amount has to be the multiple of hundred!");
        }

        // Competitive bidding is limited to 35% of the offering amount for each bidder
        if(bid.isCompetitive()) {
            if(bid.getAmount() > bid.getTreasurySecurity().getTotalAmount() * ApplicationDetails.percentage) {
                errors.put("AMOUNT_COMPETITIVE", "Competitive bidding is limited to 35% of the offering amount for each bidder!");
            }
        } else {
            // Noncompetitive bidding is limited to purchases of $5 million per auction
            if(bid.getAmount() > ApplicationDetails.max_amount) {
                errors.put("AMOUNT_NONCOMPETITIVE", "Noncompetitive bidding is limited to purchases of $5 million per auction!");
            }
        }

        if (bid.getRate() <= ApplicationDetails.min_rate || bid.getRate() > ApplicationDetails.max_rate) {
            errors.put("RATE_RANGE", "Rate has to be between 0 and 10!");
        }

        if (!errors.isEmpty()) {
            return errors;
        } else {
            bidRepository.save(bid);
            return errors;
        }
    }

    // DESTROY
    @Override
    public void disableBid(Long bidId) {

        Bid bid = bidRepository.findById(bidId).orElse(null);
        if (bid != null) {
            bid.setDisabled(true);
            bidRepository.save(bid);
        }
    }
}
