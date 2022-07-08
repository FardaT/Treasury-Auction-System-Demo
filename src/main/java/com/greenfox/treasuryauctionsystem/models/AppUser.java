package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

	// FIELDS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String email;
	private String password;
	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.MERGE,
			orphanRemoval = true
	)
	private List<Bid> bids = new ArrayList<>();
	private String institution;
	private String activationToken = PasswordResetTokenGenerator.generatePasswordResetToken();
	private LocalDateTime activationTokenExpiration = Utility.setExpiration(ApplicationDetails.expiration);
	private String reactivationToken;
	private LocalDateTime reactivationTokenExpiration;
	private boolean isAdmin = false;
	private boolean isActivated = false;
	private boolean isApproved = false;
	private boolean isDisabled = false;

	// CUSTOM
	public void adBid (Bid bid) {
		bids.add(bid);
		bid.setUser(this);
	}

	public void removeBid (Bid bid) {
		bids.remove(bid);
		bid.setUser(null);
	}

	public List<BidDTO> createDTOList (List<Bid> bids) {
		List<BidDTO> returnList = new ArrayList<>();
		for (Bid item : bids) {
			returnList.add(new BidDTO(
					item.getTreasurySecurity().getSecurityName(),
					item.isCompetitive(),
					item.getAmount(),
					item.getRate(),
					item.isAccepted(),
					item.isArchived())
			);
		}
		return returnList;
	}
}
