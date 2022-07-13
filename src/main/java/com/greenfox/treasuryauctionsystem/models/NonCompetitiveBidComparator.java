package com.greenfox.treasuryauctionsystem.models;

import java.util.Comparator;

public class NonCompetitiveBidComparator implements Comparator<Bid> {
  @Override
  public int compare(Bid firstBid, Bid secondBid) {
    return Long.compare(firstBid.getId(), firstBid.getId());
  }
}
