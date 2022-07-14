package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.utils.BotDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class BidderBot extends AppUser{
  private static int botNumber = 0;

  private static final List<String> behaviourList  = new ArrayList<>(
      Arrays.asList("institutional", "retail"));
  private String behaviour;

  private Long totalBudget;
  @Transient
  private Map<String, Long> budgetAllocation;
  @Transient
  private Map<String, Float> rateEntryPoint;
  private LocalDateTime bidActuationTime;
  @Transient
  private Random rnd = new Random();
  public BidderBot() {
    username = "BotUser_" + behaviour + "_" + botNumber++;
    password = PasswordResetTokenGenerator.generatePasswordResetToken();
    isAdmin = false;
    isApproved = true;
    isActivated = true;
    behaviour = behaviourList.get(rnd.nextInt(behaviourList.size()));
    totalBudget = generateBudget("institutional");
    budgetAllocation = budgetAllocation;
    rateEntryPoint = rateEntryPoint;
  }
  public BidderBot(String behaviour) {
    this();
    this.behaviour = behaviour;
  }
  private Long generateBudget(String behaviour) {
    if (behaviour.equals("institutional")) {
      return (rnd.nextLong(300000 - 100000) +
          100000) * 100;
    } else {
      return (rnd.nextLong(300000 - 100000) +
          100000) * 100;
    }
  }
}
