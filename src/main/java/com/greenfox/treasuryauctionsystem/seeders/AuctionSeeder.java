package com.greenfox.treasuryauctionsystem.seeders;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuctionSeeder {

    // DI
    private final AuctionRepository auctionRepository;

    public AuctionSeeder(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void saveAuctions() {

        // SECURITIES for the ongoing auction
        TreasurySecurity treasurySecurity1 = new TreasurySecurity();
        treasurySecurity1.setSecurityName("Bond 1");
        treasurySecurity1.setSecurityType("Bond");
        treasurySecurity1.setSecurityTerm("30y");
        treasurySecurity1.setTotalAmount(150_000_000);
        treasurySecurity1.setIssueDate(LocalDate.now().plusWeeks(1));
        treasurySecurity1.setMaturityDate(LocalDate.now().plusWeeks(1).plusYears(30));
        treasurySecurity1.setHighRate(9.9f);

        TreasurySecurity treasurySecurity2 = new TreasurySecurity();
        treasurySecurity2.setSecurityName("Bill 1");
        treasurySecurity2.setSecurityType("Bill");
        treasurySecurity2.setSecurityTerm("26w");
        treasurySecurity2.setTotalAmount(55_000_000);
        treasurySecurity2.setIssueDate(LocalDate.now().plusDays(10));
        treasurySecurity2.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));
        treasurySecurity2.setHighRate(8.4f);

        TreasurySecurity treasurySecurity3 = new TreasurySecurity();
        treasurySecurity3.setSecurityName("Note 1");
        treasurySecurity3.setSecurityType("Note");
        treasurySecurity3.setSecurityTerm("5y");
        treasurySecurity3.setTotalAmount(220_000_000);
        treasurySecurity3.setIssueDate(LocalDate.now().plusWeeks(2));
        treasurySecurity3.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));
        treasurySecurity3.setHighRate(11.2f);

        // ONGOING
        Auction ongoing = new Auction();
        ongoing.setAuctionStartDate(LocalDateTime.now());
        ongoing.setAuctionEndDate(LocalDateTime.now().plusDays(10));
        ongoing.addTreasurySecurity(treasurySecurity1);
        ongoing.addTreasurySecurity(treasurySecurity2);
        ongoing.addTreasurySecurity(treasurySecurity3);
        ongoing.setProcessed(false);
        ongoing.setDisabled(false);

        // UPCOMING
        Auction upcoming = new Auction();
        upcoming.setAuctionStartDate(LocalDateTime.now().plusDays(1));
        upcoming.setAuctionEndDate(LocalDateTime.now().plusDays(2));
        upcoming.setProcessed(false);
        upcoming.setDisabled(false);

        // FINISHED
        Auction finished = new Auction();
        finished.setAuctionStartDate(LocalDateTime.now());
        finished.setAuctionEndDate(LocalDateTime.now());
        finished.setProcessed(false);
        finished.setDisabled(false);

        // SAVE
        auctionRepository.save(ongoing);
        auctionRepository.save(upcoming);
        auctionRepository.save(finished);
    }

}
