package com.greenfox.treasuryauctionsystem.models;

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
		bid.setTreasurySecurity(null);
	}

	public AppUser () {
	}

	public AppUser (Long id, String username, String email, String password, List<Bid> bids, String institution, String activationToken, LocalDateTime activationTokenExpiration, String reactivationToken, LocalDateTime reactivationTokenExpiration, boolean isAdmin, boolean isActivated, boolean isApproved, boolean isDisabled) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.bids = bids;
		this.institution = institution;
		this.activationToken = activationToken;
		this.activationTokenExpiration = activationTokenExpiration;
		this.reactivationToken = reactivationToken;
		this.reactivationTokenExpiration = reactivationTokenExpiration;
		this.isAdmin = isAdmin;
		this.isActivated = isActivated;
		this.isApproved = isApproved;
		this.isDisabled = isDisabled;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public List<Bid> getBids () {
		return bids;
	}

	public void setBids (List<Bid> bids) {
		this.bids = bids;
	}

	public String getInstitution () {
		return institution;
	}

	public void setInstitution (String institution) {
		this.institution = institution;
	}

	public String getActivationToken () {
		return activationToken;
	}

	public void setActivationToken (String activationToken) {
		this.activationToken = activationToken;
	}

	public LocalDateTime getActivationTokenExpiration () {
		return activationTokenExpiration;
	}

	public void setActivationTokenExpiration (LocalDateTime activationTokenExpiration) {
		this.activationTokenExpiration = activationTokenExpiration;
	}

	public String getReactivationToken () {
		return reactivationToken;
	}

	public void setReactivationToken (String reactivationToken) {
		this.reactivationToken = reactivationToken;
	}

	public LocalDateTime getReactivationTokenExpiration () {
		return reactivationTokenExpiration;
	}

	public void setReactivationTokenExpiration (LocalDateTime reactivationTokenExpiration) {
		this.reactivationTokenExpiration = reactivationTokenExpiration;
	}

	public boolean isAdmin () {
		return isAdmin;
	}

	public void setAdmin (boolean admin) {
		isAdmin = admin;
	}

	public boolean isActivated () {
		return isActivated;
	}

	public void setActivated (boolean activated) {
		isActivated = activated;
	}

	public boolean isApproved () {
		return isApproved;
	}

	public void setApproved (boolean approved) {
		isApproved = approved;
	}

	public boolean isDisabled () {
		return isDisabled;
	}

	public void setDisabled (boolean disabled) {
		isDisabled = disabled;
	}
}
