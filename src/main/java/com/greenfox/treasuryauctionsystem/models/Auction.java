package com.greenfox.treasuryauctionsystem.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "auctions")
public class Auction {

  @Id
  @GenericGenerator(
      name = "auction-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "auctions_sequence"),
          @Parameter(name = "initial_value", value = "1000000"),
          @Parameter(name = "increment_size", value = "1")
      })
  @GeneratedValue(generator = "auction-sequence-generator")
  private Long cusip;

  @OneToMany(
      mappedBy = "auction",
      cascade = CascadeType.MERGE,
      orphanRemoval = true
  )
  private List<TreasurySecurity> treasurySecurityList = new ArrayList<>();

  @OneToMany(
      mappedBy = "auction",
      cascade = CascadeType.MERGE,
      orphanRemoval = true
  )
  private List<Bid> bidList = new ArrayList<>();

  private LocalDateTime auctionStartDate;
  private LocalDateTime auctionEndDate;
  private boolean isProcessed;

  public Auction() {
  }

  public Auction(Long cusip, List<TreasurySecurity> treasurySecurityList, List<Bid> bidList,
                 LocalDateTime auctionStartDate, LocalDateTime auctionEndDate,
                 boolean isProcessed) {
    this.cusip = cusip;
    this.treasurySecurityList = treasurySecurityList;
    this.bidList = bidList;
    this.auctionStartDate = auctionStartDate;
    this.auctionEndDate = auctionEndDate;
    this.isProcessed = isProcessed;
  }

  public Long getCusip() {
    return cusip;
  }

  public void setCusip(Long cusip) {
    this.cusip = cusip;
  }

  public List<TreasurySecurity> getTreasurySecurityList() {
    return treasurySecurityList;
  }

  public void setTreasurySecurityList(
      List<TreasurySecurity> treasurySecurityList) {
    this.treasurySecurityList = treasurySecurityList;
  }

  public List<Bid> getBidList() {
    return bidList;
  }

  public void setBidList(List<Bid> bidList) {
    this.bidList = bidList;
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

  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean processed) {
    isProcessed = processed;
  }

  public void addBid(Bid bid) {
    bidList.add(bid);
    bid.setAuction(this);
  }

  public void removeBid(Bid bid) {
    bidList.remove(bid);
    bid.setUser(null);
  }

  public void addTreasurySecurity(TreasurySecurity treasurySecurity) {
    treasurySecurityList.add(treasurySecurity);
    treasurySecurity.setAuction(this);
  }

  public void removeTreasurySecurity(TreasurySecurity treasurySecurity) {
    treasurySecurityList.remove(treasurySecurity);
    treasurySecurity.setAuction(null);
  }
}
