package com.greenfox.treasuryauctionsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotDetails {

  public static long maxBudgetInstitutional;

  public static long minBudgetInstitutional;

  public static long maxBudgetRetail;

  public static long minBudgetRetail;

  @Value("${maxBudgetInstitutional}")
  public void setMaxBudgetInstitutional(Long maxBudgetInstitutional) {
    BotDetails.maxBudgetInstitutional = maxBudgetInstitutional;
  }
  @Value("${minBudgetInstitutional}")
  public void setMinBudgetInstitutional(Long minBudgetInstitutional) {
    BotDetails.minBudgetInstitutional = minBudgetInstitutional;
  }
  @Value("${maxBudgetRetail}")
  public void setMaxBudgetRetail(Long maxBudgetRetail) {
    BotDetails.maxBudgetRetail = maxBudgetRetail;
  }
  @Value("${minBudgetRetail}")
  public void setMinBudgetRetail(Long minBudgetRetail) {
    BotDetails.minBudgetRetail = minBudgetRetail;
  }
  public BotDetails() {
  }
}
