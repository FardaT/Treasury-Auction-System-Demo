package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.models.dtos.BidResponseDTO;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {

	// FIELDS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	protected String username;
	private String email;
	protected String password;
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
	protected boolean isAdmin = false;
	protected boolean isActivated = false;
	protected boolean isApproved = false;
	private boolean isDisabled = false;

	//Bot constructor
	public AppUser(String username, String password, boolean isAdmin, boolean isActivated,
				   boolean isApproved) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isActivated = isActivated;
		this.isApproved = isApproved;
	}

	// CUSTOM
	public void adBid (Bid bid) {
		bids.add(bid);
		bid.setUser(this);
	}

	public void removeBid (Bid bid) {
		bids.remove(bid);
		bid.setUser(null);
	}

	public List<BidResponseDTO> createDTOList (List<Bid> bids) {
		List<BidResponseDTO> returnList = new ArrayList<>();
		for (Bid item : bids) {
			returnList.add(new BidResponseDTO(
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
