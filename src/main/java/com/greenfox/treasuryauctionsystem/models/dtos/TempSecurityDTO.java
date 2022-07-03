package com.greenfox.treasuryauctionsystem.models.dtos;

import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TempSecurityDTO {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String securityName;
  private String securityType;
  private String securityTerm;
  private long totalAmount;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDate issueDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDate maturityDate;

  public TempSecurityDTO(String securityType, String securityTerm,
                         long totalAmount, LocalDate issueDate) {
    this.securityName = securityTerm + " " + securityType;
    this.securityType = securityType;
    this.securityTerm = securityTerm;
    this.totalAmount = totalAmount;
    this.issueDate = issueDate;
    this.maturityDate = calculateMaturity(securityType,securityTerm,issueDate);
  }

  private LocalDate calculateMaturity(String securityType, String securityTerm, LocalDate issueDate) {
    if (securityType.equals("T-Bill")) {
      int weeks = Integer.parseInt(securityTerm.replaceAll("[\\D]", ""));
      maturityDate = issueDate.plusWeeks(weeks);
    } else {
      int years = Integer.parseInt(securityTerm.replaceAll("[\\D]", ""));
      maturityDate = issueDate.plusYears(years);
    }
    return maturityDate;
  }
}
