package com.greenfox.treasuryauctionsystem.models.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class AuctionDateDTO {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime auctionStartDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime auctionEndDate;

  public AuctionDateDTO(LocalDateTime auctionStartDate, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionEndTime) {
    this.auctionStartDate = auctionStartDate;
    this.auctionEndDate = auctionEndTime.atDate(auctionStartDate.toLocalDate());
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
