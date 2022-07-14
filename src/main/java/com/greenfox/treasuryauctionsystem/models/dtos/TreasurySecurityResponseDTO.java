package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TreasurySecurityResponseDTO {

  private Long id;
  private Long auctionId;
  private String securityName;
  private String securityType;
  private String securityTerm;
  private long totalAmount;
  private String issueDate;
  private String maturityDate;

  public TreasurySecurityResponseDTO(TreasurySecurity treasurySecurity) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    this.id = treasurySecurity.getId();
    this.auctionId = treasurySecurity.getAuction().getId();
    this.securityName = treasurySecurity.getSecurityName();
    this.securityType = treasurySecurity.getSecurityType();
    this.securityTerm = treasurySecurity.getSecurityTerm();
    this.totalAmount = treasurySecurity.getTotalAmount();
    this.issueDate = treasurySecurity.getIssueDate().format(dateFormatter);
    this.maturityDate = treasurySecurity.getMaturityDate().format(dateFormatter);
  }

  public Long getId() {
    return id;
  }

  public Long getAuctionId() {
    return auctionId;
  }

  public String getSecurityName() {
    return securityName;
  }

  public String getSecurityType() {
    return securityType;
  }

  public String getSecurityTerm() {
    return securityTerm;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public String getIssueDate() {
    return issueDate;
  }

  public String getMaturityDate() {
    return maturityDate;
  }
}
