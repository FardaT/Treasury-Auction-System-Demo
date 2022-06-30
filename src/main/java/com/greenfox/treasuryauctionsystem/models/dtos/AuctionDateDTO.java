package com.greenfox.treasuryauctionsystem.models.dtos;

import java.time.LocalDateTime;

public class AuctionDateDTO {
  private LocalDateTime auctionStartDate;
  private LocalDateTime auctionEndDate;

  public AuctionDateDTO(LocalDateTime auctionStartDate, LocalDateTime auctionEndDate) {
    this.auctionStartDate = auctionStartDate;
    this.auctionEndDate = auctionEndDate;
  }

  public LocalDateTime getAuctionStartDate() {
    return auctionStartDate;
  }

  public void setAuctionStartDate(LocalDateTime auctionStartDate) {
    this.auctionStartDate = auctionStartDate;
  }

  public LocalDateTime getAuctionEndDate() {
    return auctionEndDate;
  }

  public void setAuctionEndDate(LocalDateTime auctionEndDate) {
    this.auctionEndDate = auctionEndDate;
  }
}
