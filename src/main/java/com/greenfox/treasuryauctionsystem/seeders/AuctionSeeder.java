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

        TreasurySecurity treasurySecurity4 = new TreasurySecurity();
        treasurySecurity4.setSecurityName("Bond 2");
        treasurySecurity4.setSecurityType("Bond");
        treasurySecurity4.setSecurityTerm("30y");
        treasurySecurity4.setTotalAmount(150_000_000);
        treasurySecurity4.setIssueDate(LocalDate.now().plusWeeks(1));
        treasurySecurity4.setMaturityDate(LocalDate.now().plusWeeks(1).plusYears(30));
        treasurySecurity4.setHighRate(9.9f);

        TreasurySecurity treasurySecurity5 = new TreasurySecurity();
        treasurySecurity5.setSecurityName("Bill 2");
        treasurySecurity5.setSecurityType("Bill");
        treasurySecurity5.setSecurityTerm("26w");
        treasurySecurity5.setTotalAmount(55_000_000);
        treasurySecurity5.setIssueDate(LocalDate.now().plusDays(10));
        treasurySecurity5.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));
        treasurySecurity5.setHighRate(8.4f);

        TreasurySecurity treasurySecurity6 = new TreasurySecurity();
        treasurySecurity6.setSecurityName("Note 2");
        treasurySecurity6.setSecurityType("Note");
        treasurySecurity6.setSecurityTerm("5y");
        treasurySecurity6.setTotalAmount(220_000_000);
        treasurySecurity6.setIssueDate(LocalDate.now().plusWeeks(2));
        treasurySecurity6.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));
        treasurySecurity6.setHighRate(11.2f);

        // ******************************* //
        // ************ AUCTIONS ************ //
        // ******************************* //

        // ONGOING
        Auction ongoing = new Auction();
        ongoing.setAuctionStartDate(LocalDateTime.now());
        ongoing.setAuctionEndDate(LocalDateTime.now().plusDays(10));
        ongoing.addTreasurySecurity(treasurySecurity1);
        ongoing.addTreasurySecurity(treasurySecurity2);
        ongoing.addTreasurySecurity(treasurySecurity3);
        ongoing.setProcessed(false);
        ongoing.setDisabled(false);

        // ONGOING 2
        Auction ongoing2 = new Auction();
        ongoing2.setAuctionStartDate(LocalDateTime.now());
        ongoing2.setAuctionEndDate(LocalDateTime.now().plusDays(10));
        ongoing2.addTreasurySecurity(treasurySecurity4);
        ongoing2.addTreasurySecurity(treasurySecurity5);
        ongoing2.addTreasurySecurity(treasurySecurity6);
        ongoing2.setProcessed(false);
        ongoing2.setDisabled(false);

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

        // ******************************* //
        // ************ BIDS ************ //
        // ******************************* //

        Bid bid1 = new Bid();
        bid1.setTreasurySecurity(treasurySecurity1);
        bid1.setUser(user);
        bid1.setCompetitive(true);
        bid1.setAmount(100);
        bid1.setRate(1.1f);

        Bid bid2 = new Bid();
        bid2.setTreasurySecurity(treasurySecurity2);
        bid2.setUser(user);
        bid2.setCompetitive(true);
        bid2.setAmount(100);
        bid2.setRate(1.1f);

        Bid bid3 = new Bid();
        bid3.setTreasurySecurity(treasurySecurity3);
        bid3.setUser(user);
        bid3.setCompetitive(true);
        bid3.setAmount(100);
        bid3.setRate(1.1f);

        Bid bid4 = new Bid();
        bid4.setTreasurySecurity(treasurySecurity1);
        bid4.setUser(user2);
        bid4.setCompetitive(true);
        bid4.setAmount(100);
        bid4.setRate(1.1f);

        Bid bid5 = new Bid();
        bid5.setTreasurySecurity(treasurySecurity2);
        bid5.setUser(user2);
        bid5.setCompetitive(true);
        bid5.setAmount(100);
        bid5.setRate(1.1f);

        Bid bid6 = new Bid();
        bid6.setTreasurySecurity(treasurySecurity3);
        bid6.setUser(user2);
        bid6.setCompetitive(true);
        bid6.setAmount(100);
        bid6.setRate(1.1f);

        Bid bid7 = new Bid();
        bid7.setTreasurySecurity(treasurySecurity4);
        bid7.setUser(user);
        bid7.setCompetitive(true);
        bid7.setAmount(100);
        bid7.setRate(1.1f);

        Bid bid8 = new Bid();
        bid8.setTreasurySecurity(treasurySecurity5);
        bid8.setUser(user);
        bid8.setCompetitive(true);
        bid8.setAmount(100);
        bid8.setRate(1.1f);

        Bid bid9 = new Bid();
        bid9.setTreasurySecurity(treasurySecurity6);
        bid9.setUser(user);
        bid9.setCompetitive(true);
        bid9.setAmount(100);
        bid9.setRate(1.1f);

        Bid bid10 = new Bid();
        bid10.setTreasurySecurity(treasurySecurity4);
        bid10.setUser(user2);
        bid10.setCompetitive(true);
        bid10.setAmount(100);
        bid10.setRate(1.1f);

        Bid bid11 = new Bid();
        bid11.setTreasurySecurity(treasurySecurity5);
        bid11.setUser(user2);
        bid11.setCompetitive(true);
        bid11.setAmount(100);
        bid11.setRate(1.1f);

        Bid bid12 = new Bid();
        bid12.setTreasurySecurity(treasurySecurity6);
        bid12.setUser(user2);
        bid12.setCompetitive(true);
        bid12.setAmount(100);
        bid12.setRate(1.1f);

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
