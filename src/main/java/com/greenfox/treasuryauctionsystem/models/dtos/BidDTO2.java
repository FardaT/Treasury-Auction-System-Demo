package com.greenfox.treasuryauctionsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidDTO2 {

	private String treasurySecurity;
	private boolean isCompetitive;
	private long amount;
	private Float rate;
	private boolean isAccepted;
	private boolean isArchived;

}
