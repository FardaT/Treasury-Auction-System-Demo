package com.greenfox.treasuryauctionsystem.utils;

import com.greenfox.treasuryauctionsystem.models.Auction;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class BotTimerTaskUtil {
  BotTimerTask botTimerTask;
  ScheduledExecutorService exe = Executors.newScheduledThreadPool(3);

  public BotTimerTaskUtil(BotTimerTask botTimerTask) {
    this.botTimerTask = botTimerTask;
  }

  // Scheduler util to set up Timer with auction date
  public void scheduleBotRun(Auction auction, Integer numberOfBots){
    botTimerTask.setNumberOfBots(numberOfBots);
    botTimerTask.setAuction(auction);
    Duration duration = Duration.between(LocalDateTime.now(), auction.getAuctionStartDate());
    exe.schedule(botTimerTask, duration.toMillis(), TimeUnit.MILLISECONDS);
  }
}
