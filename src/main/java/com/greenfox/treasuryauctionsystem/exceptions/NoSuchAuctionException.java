package com.greenfox.treasuryauctionsystem.exceptions;

public class NoSuchAuctionException
    extends RuntimeException {

  public NoSuchAuctionException(String message) {
    super(message);
  }
}
