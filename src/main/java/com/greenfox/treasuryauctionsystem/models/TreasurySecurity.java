package com.greenfox.treasuryauctionsystem.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "treasury_securities")
public class TreasurySecurity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // TODO: 2022. 06. 22.  relation
  @ManyToOne(fetch = FetchType.LAZY)
  private Auction auction;

  private String securityName;
  private String securityType;
  private String securityTerm;

  public TreasurySecurity() {
  }

  public TreasurySecurity(Long id, Auction auction, String securityName, String securityType,
                          String securityTerm) {
    this.id = id;
    this.auction = auction;
    this.securityName = securityName;
    this.securityType = securityType;
    this.securityTerm = securityTerm;
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
}
