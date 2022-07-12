package com.greenfox.treasuryauctionsystem.models.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class AuctionDateDTO {
  /*@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)*/
  private LocalDateTime auctionStartDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime auctionEndDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate auctionStartDateFormat;

  public AuctionDateDTO(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate auctionStartDateFormat, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionEndTime, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionStartTime) {
    this.auctionStartDateFormat = auctionStartDateFormat;
    this.auctionStartDate = LocalDateTime.of(auctionStartDateFormat,auctionStartTime);
    this.auctionEndDate = auctionEndTime.atDate(this.auctionStartDate.toLocalDate());
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

  public LocalDate getAuctionStartDateFormat() {
    return auctionStartDateFormat;
  }

  public void setAuctionStartDateFormat(LocalDate auctionStartDateFormat) {
    this.auctionStartDateFormat = auctionStartDateFormat;
  }
}
