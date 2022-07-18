package com.greenfox.treasuryauctionsystem.models.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class AuctionDateDTO {
  private LocalDateTime auctionStartDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime auctionEndDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate auctionStartDateFormat;
  // To get the number of bots set for an auction along with the dates
  Integer numberOfBots;

/*  public AuctionDateDTO(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate auctionStartDateFormat, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionEndTime, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionStartTime) {
    this.auctionStartDateFormat = auctionStartDateFormat;
    this.auctionStartDate = LocalDateTime.of(auctionStartDateFormat,auctionStartTime);
    this.auctionEndDate = auctionEndTime.atDate(this.auctionStartDate.toLocalDate());
  }*/
  public AuctionDateDTO(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate auctionStartDateFormat, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionEndTime, @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime auctionStartTime, Integer numberOfBots) {
    this.auctionStartDateFormat = auctionStartDateFormat;
    // Next validation checkes if null
    this.auctionStartDate = auctionStartDateFormat == null || auctionStartTime == null ? null : LocalDateTime.of(auctionStartDateFormat,auctionStartTime);
    this.auctionEndDate =  auctionStartDate != null ? auctionEndTime.atDate(this.auctionStartDate.toLocalDate()) : null;
    this.numberOfBots = numberOfBots;
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

  public Integer getNumberOfBots() {
    return numberOfBots;
  }

  public void setNumberOfBots(Integer numberOfBots) {
    this.numberOfBots = numberOfBots;
  }
}
