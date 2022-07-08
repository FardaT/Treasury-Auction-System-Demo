package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.Auction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TreasurySecurityDTO {

	private Auction auction;
	private String securityName;
	private String securityType;
	private String securityTerm;
	private long totalAmount;
	private LocalDate issueDate;
	private LocalDate maturityDate;
	private float highRate;

}
