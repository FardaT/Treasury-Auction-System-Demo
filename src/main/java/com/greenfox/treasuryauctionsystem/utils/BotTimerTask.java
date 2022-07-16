package com.greenfox.treasuryauctionsystem.utils;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.BidderBot;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Slf4j
public class BotTimerTask extends TimerTask {
  private BidRepository bidRepository;
  private AppUserRepository appUserRepository;
  private Auction auction;
  Integer numberOfBots;
  private List<BidderBot> bidderBots = new ArrayList<>();

  public BotTimerTask(BidRepository bidRepository, AppUserRepository appUserRepository) {
    this.bidRepository = bidRepository;
    this.appUserRepository = appUserRepository;
  }

  @Override
  public void run() {
    createBots();
    try {
      createBidsForBots();
    } catch (InterruptedException e) {
      log.error("Failed to create bid after delay, proceeded without bid");
    }
  }
  private void createBots() {
    for (int i = 0; i < numberOfBots; i++) {
      bidderBots.add(new BidderBot());
    }
    appUserRepository.saveAll(bidderBots);
  }
  private void createBidsForBots() throws InterruptedException {
    for (BidderBot bot: bidderBots) {
      bot.setBids(bot.generateBids(auction));
      bidRepository.saveAll(bot.getBids());
      Thread.sleep((long)((Math.random() * (25000 - 9000)) + 9000));
      if(auction.getAuctionEndDate().isBefore(LocalDateTime.now())){
        break;
      }
    }
  }
}