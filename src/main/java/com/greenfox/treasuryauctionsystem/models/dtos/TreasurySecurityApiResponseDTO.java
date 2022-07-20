package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TreasurySecurityApiResponseDTO {

	private Long id;
	private Long auctionId;
	private String securityName;
	private String securityType;
	private String securityTerm;
	private long totalAmount;
	private String issueDate;
	private String maturityDate;

	public TreasurySecurityApiResponseDTO (TreasurySecurity treasurySecurity) {
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

	public static List<TreasurySecurityApiResponseDTO> create (List<TreasurySecurity> treasurySecurityList) {
		List<TreasurySecurityApiResponseDTO> returnTreasurySecurityApiResponseDTOS = new ArrayList<>();

		for (TreasurySecurity item : treasurySecurityList) {
			returnTreasurySecurityApiResponseDTOS.add(new TreasurySecurityApiResponseDTO(item));
		}

		return returnTreasurySecurityApiResponseDTOS;
	}
}
