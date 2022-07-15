package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BidderBot extends AppUser{
  private static int botNumber = 1;
  private static final List<String> behaviourList  = new ArrayList<>(
      Arrays.asList("institutional", "retail"));
  private String behaviour;
  @Transient
  private List<Bid> bids;
  @Transient
  private Random rnd = new Random();
  public BidderBot(Auction auction) {
    password = PasswordResetTokenGenerator.generatePasswordResetToken();
    isAdmin = false;
    isApproved = true;
    isActivated = true;
    behaviour = behaviourList.get(rnd.nextInt(behaviourList.size()));
    username = "BotUser_" + behaviour + "_" + botNumber++;
    bids = generateBudgetAllocation(behaviour, auction);
  }
  public BidderBot(Auction auction,String behaviour) {
    this(auction);
    this.behaviour = behaviour;
  }
  private List<Bid> generateBudgetAllocation(String behaviour, Auction auction){
    List<Bid> bidList = new ArrayList<>();
    long maxNonCompetitiveBid = 50000L; // 50000 times 100 (down below) to get denominations in $5 mil limit
    for(TreasurySecurity sec : auction.getTreasurySecurityList()){
      int maxCompetitiveBid = (int)(sec.getTotalAmount() * 0.35) / 100; // to get the product of 100 denominations
      // Chance of not bidding for a security - skip to next one in list
      if(rnd.nextFloat(1.0f)*0.7f > 0.5f){
        continue;
      }
      Bid tempBid = new Bid();
      tempBid.setTreasurySecurity(sec);
      tempBid.setUser(this);
      // How to act when the bot is institutional investor: more likely to bid competitively, more budget, tend to buy both long and short term securities, smaller variety of rates
      if(behaviour.equals("institutional")){
        boolean weightedBidTypeProbability = rnd.nextFloat(1.0f)*1.5f > 0.5f;
        long institutionalCompetitiveBidAmount = (rnd.nextLong(maxCompetitiveBid - 7000) + 7000) * 100;
        long institutionalNonCompetitiveBidAmount = (rnd.nextLong(maxNonCompetitiveBid - 5000) + 5000) * 100;
        // Different rates and amounts for each types of security whether they are competitive or non-competitive bids
        switch (sec.getSecurityType()){
          case "T-Bill":
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(institutionalCompetitiveBidAmount);
              tempBid.setRate(rnd.nextFloat(3.000f - 1.100f) + 1.100f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(institutionalNonCompetitiveBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
          case "T-Note":
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(institutionalCompetitiveBidAmount);
              tempBid.setRate(rnd.nextFloat(3.200f - 2.500f) + 2.500f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(institutionalNonCompetitiveBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
          case "T-Bond":
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(institutionalCompetitiveBidAmount);
              tempBid.setRate(rnd.nextFloat(4.500f - 1.200f) + 1.200f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(institutionalNonCompetitiveBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
        }
        }
      // How to act when the bot is retail investor: more likely to bid non-competitively, less budget, shorter terms, higher variety of rates
      else {
        boolean weightedBidTypeProbability = rnd.nextFloat(1.0f)*0.7f > 0.5f; // retail investor tend to be non-competitive
        long retailBidAmount = (rnd.nextLong(8000 - 100) + 100) * 100; // times 100 for $100 denominations
        switch (sec.getSecurityType()){
          case "T-Bill":
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(retailBidAmount);
              tempBid.setRate(rnd.nextFloat(4.400f - 0.800f) + 0.800f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(retailBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
          case "T-Note":
            //retail more likely to skip on longer terms like T-notes and T-bonds
            if(rnd.nextFloat(1.0f)*0.8f > 0.5f){
              continue;
            }
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(retailBidAmount);
              tempBid.setRate(rnd.nextFloat(4.000f - 2.000f) + 2.000f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(retailBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
          case "T-Bond":
            //retail more likely to skip on longer terms like T-notes and T-bonds
            if(rnd.nextFloat(1.0f)*0.6f > 0.5f){
              continue;
            }
            if(weightedBidTypeProbability){
              tempBid.setCompetitive(true);
              tempBid.setAmount(retailBidAmount);
              tempBid.setRate(rnd.nextFloat(4.500f - 1.800f) + 1.800f);
            } else {
              tempBid.setCompetitive(false);
              tempBid.setAmount(retailBidAmount);
              maxNonCompetitiveBid = maxNonCompetitiveBid - tempBid.getAmount();
              tempBid.setRate(0.0f);
            }
            break;
        }
      }
      bidList.add(tempBid);
    }
    return bidList;
  }
}
