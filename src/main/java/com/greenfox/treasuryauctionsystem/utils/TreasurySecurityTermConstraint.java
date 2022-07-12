package com.greenfox.treasuryauctionsystem.utils;

import java.util.ArrayList;
import java.util.Arrays;


public class TreasurySecurityTermConstraint {

  //Temporary validations
  public static final ArrayList<String> validSecurities  = new ArrayList<>(
      Arrays.asList("T-Bill", "T-Note", "T-Bond"));
  public static final ArrayList<String> validBillTerm  = new ArrayList<>(
      Arrays.asList("4-week", "8-week", "13-week", "26-week", "52-week"));

  public static final ArrayList<String> validNoteTerm  = new ArrayList<>(
      Arrays.asList("2-year", "3-year", "5-year", "7-year", "10-year"));
  public static final ArrayList<String> validBondTerm  = new ArrayList<>(
      Arrays.asList("20-year", "30-year"));

}
