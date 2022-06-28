package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
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
  private boolean isAdmin;
  private String institution;
  private boolean isActivated;

  private boolean isApproved;

  private boolean isDisabled;

  private String activationToken;
  private LocalDateTime activationTokenExpiration;
  private String reactivationToken;
  private LocalDateTime reactivationTokenExpiration;

    // CUSTOM
    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setUser(this);
    }

  public AppUser(Long id, String username, String password, String email, List<Bid> bids,
                 boolean isAdmin, String institution, boolean isActivated, boolean isApproved,
                 boolean isDisabled, String activationToken,
                 LocalDateTime activationTokenExpiration,
                 String reactivationToken, LocalDateTime reactivationTokenExpiration) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.bids = bids;
    this.isAdmin = isAdmin;
    this.institution = institution;
    this.isActivated = isActivated;
    this.isApproved = isApproved;
    this.isDisabled = isDisabled;
    this.activationToken = activationToken;
    this.activationTokenExpiration = activationTokenExpiration;
    this.reactivationToken = reactivationToken;
    this.reactivationTokenExpiration = reactivationTokenExpiration;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Bid> getBids() {
    return bids;
  }

  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public boolean isActivated() {
    return isActivated;
  }

  public void setActivated(boolean activated) {
    isActivated = activated;
  }

  public boolean isApproved() {
    return isApproved;
  }

  public void setApproved(boolean approved) {
    isApproved = approved;
  }

  public boolean isDisabled() {
    return isDisabled;
  }

  public void setDisabled(boolean disabled) {
    isDisabled = disabled;
  }

  public String getActivationToken() {
    return activationToken;
  }

  public void setActivationToken(String activationToken) {
    this.activationToken = activationToken;
  }

  public LocalDateTime getActivationTokenExpiration() {
    return activationTokenExpiration;
  }

  public void setActivationTokenExpiration(LocalDateTime activationTokenExpiration) {
    this.activationTokenExpiration = activationTokenExpiration;
  }

  public String getReactivationToken() {
    return reactivationToken;
  }

  public void setReactivationToken(String reactivationToken) {
    this.reactivationToken = reactivationToken;
  }

  public LocalDateTime getReactivationTokenExpiration() {
    return reactivationTokenExpiration;
  }

  public void setReactivationTokenExpiration(LocalDateTime reactivationTokenExpiration) {
    this.reactivationTokenExpiration = reactivationTokenExpiration;
  }

  public void adBid(Bid bid) {
    bids.add(bid);
    bid.setUser(this);
  }

  public void removeBid(Bid bid) {
    bids.remove(bid);
    bid.setTreasurySecurity(null);
  }
}
