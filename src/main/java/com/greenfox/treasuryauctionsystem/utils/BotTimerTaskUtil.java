package com.greenfox.treasuryauctionsystem.utils;

import com.greenfox.treasuryauctionsystem.models.Auction;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import org.springframework.stereotype.Component;

@Component
public class BotTimerTaskUtil {
  BotTimerTask botTimerTask;

  public BotTimerTaskUtil(BotTimerTask botTimerTask) {
    this.botTimerTask = botTimerTask;
  }

  // Scheduler util to set-up Timer with auction date
  public void scheduleBotRun(Auction auction, Integer numberOfBots){
    botTimerTask.setNumberOfBots(numberOfBots);
    botTimerTask.setAuction(auction);
    Timer timer= new Timer(true);
    LocalDateTime auctionTime = auction.getAuctionStartDate();
    timer.schedule(botTimerTask, Date.from(auctionTime.atZone(ZoneId.systemDefault()).toInstant()));
  }
}
