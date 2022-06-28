package com.greenfox.treasuryauctionsystem.utils;

import java.security.SecureRandom;

public class PasswordResetTokenGenerator {

  static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  static SecureRandom rnd = new SecureRandom();

  public static String generatePasswordResetToken() {
    int len = 48;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      sb.append(ABC.charAt(rnd.nextInt(ABC.length())));
    }
    return sb.toString();
  }
}
