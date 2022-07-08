package com.greenfox.treasuryauctionsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BidDTO {

	private String treasurySecurity;
	private boolean isCompetitive;
	private long amount;
	private float rate;
	private boolean isAccepted;
	private boolean isArchived;

}
