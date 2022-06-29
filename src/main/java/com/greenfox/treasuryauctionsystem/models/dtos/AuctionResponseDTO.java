package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.Auction;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class AuctionResponseDTO {

  private Long id;
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

  public void setId(Long id) {
    this.id = id;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean processed) {
    isProcessed = processed;
  }

  public boolean isDisabled() {
    return isDisabled;
  }

  public void setDisabled(boolean disabled) {
    isDisabled = disabled;
  }
}
