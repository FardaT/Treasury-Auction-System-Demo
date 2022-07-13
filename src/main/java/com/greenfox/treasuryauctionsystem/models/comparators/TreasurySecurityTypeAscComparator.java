package com.greenfox.treasuryauctionsystem.models.comparators;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.TreasurySecurityResponseDTO;
import java.util.Comparator;

public class TreasurySecurityTypeAscComparator implements Comparator<TreasurySecurity> {

  @Override
  public int compare(TreasurySecurity o1, TreasurySecurity o2) {
    return o1.getSecurityType().compareTo(o2.getSecurityType());
  }
}
