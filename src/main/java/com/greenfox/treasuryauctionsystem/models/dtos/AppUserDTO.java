package com.greenfox.treasuryauctionsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppUserDTO {

	private String username;
	private String email;
	private String institution;
	private boolean isAdmin;
	private boolean isActivated;
	private boolean isApproved;
	private boolean isDisabled;
	private List<BidResponseDTO> bids = new ArrayList<>();

}
