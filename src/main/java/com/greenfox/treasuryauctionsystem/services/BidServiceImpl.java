package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
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
    public Map<String, String> saveBid(List<BidDTO> bidDTOS, AppUser appUser) {

        Map<String, String> errors = new HashMap<>();

        // do we have at least one bid?
        boolean allEmpty = true;
        for (BidDTO bidDTO : bidDTOS) {
            if(bidDTO.getAmount() != 0) {
                allEmpty = false;
            }
        }

        // do we have at least one bid?
        if(allEmpty) {
            errors.put("AT_LEAST_ONE", "You have to place at least one bid!");
        } else {

            int index = 0;
            int sum = 0;
            for (BidDTO bidDTO : bidDTOS) {

                // CREATE BID OBJECTS FROM INCOMING DTOs
                Bid bid = new Bid(bidDTO);

                // VALIDATIONS

                // to see if we need validation
                if(bid.getAmount() != 0) {

                    if (bid.getAmount() < 0) {
                        errors.put("AMOUNT_POSITIVE_" + index, "Amount has to be a positive number!");
                    }
                    if (bid.getAmount() % ApplicationDetails.multiple_of != 0) {
                        errors.put("AMOUNT_HUNDRED_" + index, "Amount has to be the multiple of hundred!");
                    }

                    // Competitive bidding is limited to 35% of the offering amount for each bidder
                    if (bid.isCompetitive()) {
                        if (bid.getAmount() > bid.getTreasurySecurity().getTotalAmount() * ApplicationDetails.percentage) {
                            errors.put("AMOUNT_COMPETITIVE_" + index, "Competitive bidding is limited to 35% of the offering amount for each bidder!");
                        }
                    } else {
                        // Noncompetitive bidding is limited to purchases of $5 million per auction
                        sum += bid.getAmount();
                    }

                    if (bid.getRate() <= ApplicationDetails.min_rate || bid.getRate() > ApplicationDetails.max_rate) {
                        errors.put("RATE_RANGE_" + index, "Rate has to be between 0 and 10!");
                    }

                    index++;

                }
            }

            if (sum > ApplicationDetails.max_amount) {
                errors.put("AMOUNT_NONCOMPETITIVE", "Noncompetitive bidding is limited to purchases of $5 million per auction!");
            }

            if (errors.isEmpty()) {
                for (BidDTO bidDTO : bidDTOS) {
                    // CREATE BID OBJECTS FROM INCOMING DTOs
                    Bid bid = new Bid(bidDTO);
                    bid.setUser(appUser);
                    if (bid.getAmount() != 0) {
                        bidRepository.save(bid);
                    }
                }
            }

        }
        return errors;
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
