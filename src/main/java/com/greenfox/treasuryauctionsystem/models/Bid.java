package com.greenfox.treasuryauctionsystem.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bids")
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Auction auction;

  @ManyToOne(fetch = FetchType.LAZY)
  private AppUser user;

  private boolean isCompetitive;
  private long amount;
  private float rate;
  private boolean isAccepted;
  private boolean isArchived;

  public Bid() {
  }

  public Bid(Long id, Auction auction, AppUser user, boolean isCompetitive, long amount, float rate,
             boolean isAccepted, boolean isArchived) {
    this.id = id;
    this.auction = auction;
    this.user = user;
    this.isCompetitive = isCompetitive;
    this.amount = amount;
    this.rate = rate;
    this.isAccepted = isAccepted;
    this.isArchived = isArchived;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Auction getAuction() {
    return auction;
  }

  public void setAuction(Auction auction) {
    this.auction = auction;
  }

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser user) {
    this.user = user;
  }

  public boolean isCompetitive() {
    return isCompetitive;
  }

  public void setCompetitive(boolean competitive) {
    isCompetitive = competitive;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public boolean isAccepted() {
    return isAccepted;
  }

  public void setAccepted(boolean accepted) {
    isAccepted = accepted;
  }

  public boolean isArchived() {
    return isArchived;
  }

  public void setArchived(boolean archived) {
    isArchived = archived;
  }
}
