package com.greenfox.treasuryauctionsystem.models.dtos;

import java.util.ArrayList;
import java.util.List;
public class BidsCreationDto {
    private List<BidDTO> bidDTOS;

    // default and parameterized constructor
    public BidsCreationDto() {
        this.bidDTOS = new ArrayList<>();
    }

    public BidsCreationDto(List<BidDTO> bids) {
        this.bidDTOS = bids;
    }

    // getter and setter
    public List<BidDTO> getBidDTOS() {
        return bidDTOS;
    }

    public void setBidDTOS(List<BidDTO> bidDTOS) {
        this.bidDTOS = bidDTOS;
    }

    // custom
    public void addBid(BidDTO bid) {
        this.bidDTOS.add(bid);
    }
}
