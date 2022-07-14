package com.greenfox.treasuryauctionsystem.models.comparators;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.TreasurySecurityResponseDTO;
import java.util.Comparator;

public class TreasurySecurityIssueDateAscComparator implements Comparator<TreasurySecurity> {

  @Override
  public int compare(TreasurySecurity o1, TreasurySecurity o2) {
    return o1.getIssueDate().compareTo(o2.getIssueDate());
  }
}
