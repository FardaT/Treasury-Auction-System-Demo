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

  private long offeringAmount;
  private LocalDateTime auctionStartDate;
  private LocalDateTime auctionEndDate;
  private boolean isProcessed;
  private LocalDate issueDate;
  private LocalDate maturityDate;
  private float highRate;

  public Auction() {
  }

  public Auction(Long cusip, List<TreasurySecurity> treasurySecurities, List<Bid> bidList,
                 long offeringAmount, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate,
                 boolean isProcessed, LocalDate issueDate, LocalDate maturityDate, float highRate) {
    this.cusip = cusip;
    this.treasurySecurityList = treasurySecurities;
    this.bidList = bidList;
    this.offeringAmount = offeringAmount;
    this.auctionStartDate = auctionStartDate;
    this.auctionEndDate = auctionEndDate;
    this.isProcessed = isProcessed;
    this.issueDate = issueDate;
    this.maturityDate = maturityDate;
    this.highRate = highRate;
  }

  public Long getCusip() {
    return cusip;
  }

  public void setCusip(Long cusip) {
    this.cusip = cusip;
  }

  public List<TreasurySecurity> getTreasurySecurities() {
    return treasurySecurityList;
  }

  public void setTreasurySecurities(
      List<TreasurySecurity> treasurySecurities) {
    this.treasurySecurityList = treasurySecurities;
  }

  public List<Bid> getBidList() {
    return bidList;
  }

  public void setBidList(List<Bid> bidList) {
    this.bidList = bidList;
  }

  public long getOfferingAmount() {
    return offeringAmount;
  }

  public void setOfferingAmount(long offeringAmount) {
    this.offeringAmount = offeringAmount;
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

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public LocalDate getMaturityDate() {
    return maturityDate;
  }

  public void setMaturityDate(LocalDate maturityDate) {
    this.maturityDate = maturityDate;
  }

  public float getHighRate() {
    return highRate;
  }

  public void setHighRate(float highRate) {
    this.highRate = highRate;
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
