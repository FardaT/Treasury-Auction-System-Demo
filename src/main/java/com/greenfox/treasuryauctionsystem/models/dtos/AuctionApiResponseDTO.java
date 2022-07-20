package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuctionApiResponseDTO {

    private Long id;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private boolean isProcessed;
    private boolean isDisabled;
    private List<TreasurySecurityApiResponseDTO> treasurySecurityList;

    public AuctionApiResponseDTO (Auction auction) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        this.id = auction.getId();
        this.treasurySecurityList = TreasurySecurityApiResponseDTO.create(auction.getTreasurySecurityList());
        this.startDate = auction.getAuctionStartDate().format(dateFormatter);
        this.startTime = auction.getAuctionStartDate().format(timeFormatter);
        this.endDate = auction.getAuctionEndDate().format(dateFormatter);
        this.endTime = auction.getAuctionEndDate().format(timeFormatter);
        this.isProcessed = auction.isProcessed();
        this.isDisabled = auction.isDisabled();


    }
}