package com.greenfox.treasuryauctionsystem.models;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreasurySecurityConstraint {

  //Temporary validations

  public static final ArrayList<String> validTreasury  = new ArrayList<>(
      Arrays.asList("T-bill", "Seadog", "Really", "Tiptop"));



}
