package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuctionResponseDTO {

  private Long id;

  private List<TreasurySecurity> treasurySecurityList;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private boolean isProcessed;
  private boolean isDisabled;

  public AuctionResponseDTO(Auction auction) {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    this.id = auction.getId();
    this.treasurySecurityList = auction.getTreasurySecurityList();
    this.startDate = auction.getAuctionStartDate().format(dateFormatter);
    this.startTime = auction.getAuctionStartDate().format(timeFormatter);
    this.endDate = auction.getAuctionEndDate().format(dateFormatter);
    this.endTime = auction.getAuctionEndDate().format(timeFormatter);
    this.isProcessed = auction.isProcessed();
    this.isDisabled = auction.isDisabled();
  }

  public Long getId() {
    return id;
  }

  public List<TreasurySecurity> getTreasurySecurityList() {
    return treasurySecurityList;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getEndTime() {
    return endTime;
  }

  public boolean isProcessed() {
    return isProcessed;
  }

  public boolean isDisabled() {
    return isDisabled;
  }
}
