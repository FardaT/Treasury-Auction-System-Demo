package com.greenfox.treasuryauctionsystem.seeders;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuctionSeeder {

    // DI
    private final AuctionRepository auctionRepository;
    private final AppUserRepository appUserRepository;
    private final BidRepository bidRepository;

    public AuctionSeeder(AuctionRepository auctionRepository, AppUserRepository appUserRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.appUserRepository = appUserRepository;
        this.bidRepository = bidRepository;
    }

    public void saveAuctions() {

        // ******************************* //
        // ************ USERS ************ //
        // ******************************* //

        // ADMIN
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setEmail("lukacs.dvid@gmail.com");
        admin.setPassword("$2a$10$beHgCKv8GmR2l3.V0bMSQeiY7Ifx2uuM2ed7NmDAU4UsOccegAgaC"); //Auction0123!?#
        admin.setInstitution("Morgan Stanley");
        admin.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        admin.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        admin.setAdmin(true);
        admin.setActivated(true);
        admin.setApproved(true);
        admin.setDisabled(false);

        // USER
        AppUser user = new AppUser();
        user.setUsername("user");
        user.setEmail("lukacs.dvid@gmail.com");
        user.setPassword("$2a$10$beHgCKv8GmR2l3.V0bMSQeiY7Ifx2uuM2ed7NmDAU4UsOccegAgaC"); //Auction0123!?#
        user.setInstitution("Morgan Stanley");
        user.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        user.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        user.setAdmin(false);
        user.setActivated(true);
        user.setApproved(true);
        user.setDisabled(false);

        // USER 2
        AppUser user2 = new AppUser();
        user2.setUsername("user2");
        user2.setEmail("lukacs.dvid@gmail.com");
        user2.setPassword("$2a$10$beHgCKv8GmR2l3.V0bMSQeiY7Ifx2uuM2ed7NmDAU4UsOccegAgaC"); //Auction0123!?#
        user2.setInstitution("Morgan Stanley");
        user2.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        user2.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        user2.setAdmin(false);
        user2.setActivated(true);
        user2.setApproved(true);
        user2.setDisabled(false);

        // ******************************* //
        // ************ SECURITIES ************ //
        // ******************************* //

        // SECURITIES for the ongoing auction
        TreasurySecurity treasurySecurity1 = new TreasurySecurity();
        treasurySecurity1.setSecurityName("30-year T-Bond");
        treasurySecurity1.setSecurityType("T-Bond");
        treasurySecurity1.setSecurityTerm("30-year");
        treasurySecurity1.setTotalAmount(95_000_000);
        treasurySecurity1.setIssueDate(LocalDate.now().plusWeeks(1));
        treasurySecurity1.setMaturityDate(LocalDate.now().plusWeeks(1).plusYears(30));
        treasurySecurity1.setHighRate(3.350f);

        TreasurySecurity treasurySecurity2 = new TreasurySecurity();
        treasurySecurity2.setSecurityName("26-week T-Bill");
        treasurySecurity2.setSecurityType("T-Bill");
        treasurySecurity2.setSecurityTerm("26-week");
        treasurySecurity2.setTotalAmount(65_000_000);
        treasurySecurity2.setIssueDate(LocalDate.now().plusDays(10));
        treasurySecurity2.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));
        treasurySecurity2.setHighRate(1.808f);

        TreasurySecurity treasurySecurity3 = new TreasurySecurity();
        treasurySecurity3.setSecurityName("5-year T-Note");
        treasurySecurity3.setSecurityType("T-Note");
        treasurySecurity3.setSecurityTerm("5-year");
        treasurySecurity3.setTotalAmount(100_000_000);
        treasurySecurity3.setIssueDate(LocalDate.now().plusWeeks(2));
        treasurySecurity3.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));
        treasurySecurity3.setHighRate(1.645f);

        TreasurySecurity treasurySecurity4 = new TreasurySecurity();
        treasurySecurity4.setSecurityName("30-year T-Bond");
        treasurySecurity4.setSecurityType("T-Bond");
        treasurySecurity4.setSecurityTerm("30-year");
        treasurySecurity4.setTotalAmount(90_000_000);
        treasurySecurity4.setIssueDate(LocalDate.now().plusWeeks(1));
        treasurySecurity4.setMaturityDate(LocalDate.now().plusWeeks(1).plusYears(30));
        treasurySecurity4.setHighRate(2.990f);

        TreasurySecurity treasurySecurity5 = new TreasurySecurity();
        treasurySecurity5.setSecurityName("52-week T-Bill");
        treasurySecurity5.setSecurityType("T-Bill");
        treasurySecurity5.setSecurityTerm("52-week");
        treasurySecurity5.setTotalAmount(55_000_000);
        treasurySecurity5.setIssueDate(LocalDate.now().plusDays(10));
        treasurySecurity5.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));
        treasurySecurity5.setHighRate(2.174f);

        TreasurySecurity treasurySecurity6 = new TreasurySecurity();
        treasurySecurity6.setSecurityName("5-year T-Note");
        treasurySecurity6.setSecurityType("T-Note");
        treasurySecurity6.setSecurityTerm("5-year");
        treasurySecurity6.setTotalAmount(45_000_000);
        treasurySecurity6.setIssueDate(LocalDate.now().plusWeeks(2));
        treasurySecurity6.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));
        treasurySecurity6.setHighRate(2.722f);

        // ******************************* //
        // ************ AUCTIONS ************ //
        // ******************************* //

        // ONGOING
        Auction ongoing = new Auction();
        ongoing.setAuctionStartDate(LocalDateTime.now());
        ongoing.setAuctionEndDate(LocalDateTime.now().plusMinutes(75));
        ongoing.addTreasurySecurity(treasurySecurity1);
        ongoing.addTreasurySecurity(treasurySecurity2);
        ongoing.addTreasurySecurity(treasurySecurity3);
        ongoing.setProcessed(false);
        ongoing.setDisabled(false);

        // ONGOING 2
        Auction ongoing2 = new Auction();
        ongoing2.setAuctionStartDate(LocalDateTime.now());
        ongoing2.setAuctionEndDate(LocalDateTime.now().plusMinutes(55));
        ongoing2.addTreasurySecurity(treasurySecurity4);
        ongoing2.addTreasurySecurity(treasurySecurity5);
        ongoing2.addTreasurySecurity(treasurySecurity6);
        ongoing2.setProcessed(false);
        ongoing2.setDisabled(false);

        // UPCOMING
        Auction upcoming = new Auction();
        upcoming.setAuctionStartDate(LocalDateTime.now().plusDays(1));
        upcoming.setAuctionEndDate(LocalDateTime.now().plusDays(1).plusMinutes(45));
        upcoming.setProcessed(false);
        upcoming.setDisabled(false);

        // FINISHED
        Auction finished = new Auction();
        finished.setAuctionStartDate(LocalDateTime.now().minusDays(3).minusHours(2));
        finished.setAuctionEndDate(LocalDateTime.now().minusDays(3).minusHours(1));
        finished.setProcessed(false);
        finished.setDisabled(false);

        // ******************************* //
        // ************ BIDS ************ //
        // ******************************* //

        Bid bid1 = new Bid();
        bid1.setTreasurySecurity(treasurySecurity1);
        bid1.setUser(user);
        bid1.setCompetitive(true);
        bid1.setAmount(500000);
        bid1.setRate(1.888f);

        Bid bid2 = new Bid();
        bid2.setTreasurySecurity(treasurySecurity2);
        bid2.setUser(user);
        bid2.setCompetitive(true);
        bid2.setAmount(2000000);
        bid2.setRate(1.232f);

        Bid bid3 = new Bid();
        bid3.setTreasurySecurity(treasurySecurity3);
        bid3.setUser(user);
        bid3.setCompetitive(true);
        bid3.setAmount(1100000);
        bid3.setRate(2.5f);

        Bid bid4 = new Bid();
        bid4.setTreasurySecurity(treasurySecurity1);
        bid4.setUser(user2);
        bid4.setCompetitive(true);
        bid4.setAmount(133000);
        bid4.setRate(2.110f);

        Bid bid5 = new Bid();
        bid5.setTreasurySecurity(treasurySecurity2);
        bid5.setUser(user2);
        bid5.setCompetitive(true);
        bid5.setAmount(50000);
        bid5.setRate(1.934f);

        Bid bid6 = new Bid();
        bid6.setTreasurySecurity(treasurySecurity3);
        bid6.setUser(user2);
        bid6.setCompetitive(true);
        bid6.setAmount(10000);
        bid6.setRate(1.101f);

        Bid bid7 = new Bid();
        bid7.setTreasurySecurity(treasurySecurity4);
        bid7.setUser(user);
        bid7.setCompetitive(true);
        bid7.setAmount(2030000);
        bid7.setRate(3.189f);

        Bid bid8 = new Bid();
        bid8.setTreasurySecurity(treasurySecurity5);
        bid8.setUser(user);
        bid8.setCompetitive(true);
        bid8.setAmount(2700000);
        bid8.setRate(2.200f);

        Bid bid9 = new Bid();
        bid9.setTreasurySecurity(treasurySecurity6);
        bid9.setUser(user);
        bid9.setCompetitive(true);
        bid9.setAmount(280000);
        bid9.setRate(2.489f);

        Bid bid10 = new Bid();
        bid10.setTreasurySecurity(treasurySecurity4);
        bid10.setUser(user2);
        bid10.setCompetitive(true);
        bid10.setAmount(130000);
        bid10.setRate(0.980f);

        Bid bid11 = new Bid();
        bid11.setTreasurySecurity(treasurySecurity5);
        bid11.setUser(user2);
        bid11.setCompetitive(true);
        bid11.setAmount(1220000);
        bid11.setRate(2.100f);

        Bid bid12 = new Bid();
        bid12.setTreasurySecurity(treasurySecurity6);
        bid12.setUser(user2);
        bid12.setCompetitive(true);
        bid12.setAmount(1000000);
        bid12.setRate(1.900f);

        // ******************************* //
        // ************ SAVE ************ //
        // ******************************* //

        // users
        appUserRepository.save(admin);
        appUserRepository.save(user);
        appUserRepository.save(user2);

        // auctions
        auctionRepository.save(ongoing);
        auctionRepository.save(ongoing2);
        auctionRepository.save(upcoming);
        auctionRepository.save(finished);

        // bids
        bidRepository.save(bid1);
        bidRepository.save(bid2);
        bidRepository.save(bid3);
        bidRepository.save(bid4);
        bidRepository.save(bid5);
        bidRepository.save(bid6);
        bidRepository.save(bid7);
        bidRepository.save(bid8);
        bidRepository.save(bid9);
        bidRepository.save(bid10);
        bidRepository.save(bid11);
        bidRepository.save(bid12);
    }

}
