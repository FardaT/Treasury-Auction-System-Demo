package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
  private boolean isAdmin = false;
  private String institution;
  private String activationToken = Utility.generateString();
  private Date activationTokenExpiration = Utility.setExpiration(60); // current time + 60 minutes
  private boolean isActivated = false;
  private String reactivationToken = Utility.generateString();
  private Date reactivationTokenExpiration = Utility.setExpiration(60); // current time + 60 minutes;

  // CUSTOM
  public void addBid(Bid bid) {
    bids.add(bid);
    bid.setUser(this);
  }

  public void removeBid(Bid bid) {
    bids.remove(bid);
    bid.setUser(null);
  }
}
