package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.models.dtos.TempSecurityDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "treasury_securities")
public class TreasurySecurity{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(
      mappedBy = "treasurySecurity",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private final List<Bid> bidList = new ArrayList<>();
  @ManyToOne(fetch = FetchType.LAZY)
  private Auction auction;
  private String securityName;
  private String securityType;
  private String securityTerm;
  private long totalAmount;
  private LocalDate issueDate;
  private LocalDate maturityDate;
  private float highRate;


  public TreasurySecurity() {
  }

  public TreasurySecurity(Long id, Auction auction, String securityName, String securityType,
                          String securityTerm, long totalAmount, LocalDate issueDate,
                          LocalDate maturityDate, float highRate) {
    this.id = id;
    this.auction = auction;
    this.securityName = securityName;
    this.securityType = securityType;
    this.securityTerm = securityTerm;
    this.totalAmount = totalAmount;
    this.issueDate = issueDate;
    this.maturityDate = maturityDate;
    this.highRate = highRate;
  }
  public TreasurySecurity(TempSecurityDTO tempSecurityDTO) {
    this.securityName = tempSecurityDTO.getSecurityName();
    this.securityType = tempSecurityDTO.getSecurityType();
    this.securityTerm = tempSecurityDTO.getSecurityTerm();
    this.totalAmount = tempSecurityDTO.getTotalAmount();
    this.issueDate = tempSecurityDTO.getIssueDate();
    this.maturityDate = tempSecurityDTO.getMaturityDate();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Auction getAuction() {
    return auction;
  }

  public void setAuction(Auction auction) {
    this.auction = auction;
  }

  public String getSecurityName() {
    return securityName;
  }

  public void setSecurityName(String securityName) {
    this.securityName = securityName;
  }

  public String getSecurityType() {
    return securityType;
  }

  public void setSecurityType(String securityType) {
    this.securityType = securityType;
  }

  public String getSecurityTerm() {
    return securityTerm;
  }

  public void setSecurityTerm(String securityTerm) {
    this.securityTerm = securityTerm;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(long totalAmount) {
    this.totalAmount = totalAmount;
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
    bid.setTreasurySecurity(this);
  }

  public void removeBid(Bid bid) {
    bidList.remove(bid);
    bid.setTreasurySecurity(null);
  }

  public List<Bid> getBidList() {
    return bidList;
  }
}
