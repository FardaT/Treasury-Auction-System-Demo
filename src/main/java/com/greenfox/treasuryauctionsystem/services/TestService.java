package com.greenfox.treasuryauctionsystem.services;


import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.BidderBot;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import com.greenfox.treasuryauctionsystem.seeders.AuctionSeeder;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  private AppUserRepository appUserRepository;
  private BidRepository bidRepository;
  private AuctionRepository auctionRepository;
  private TreasurySecurityRepository treasurySecurityRepository;

  // SEEDERS
  private final AuctionSeeder auctionSeeder;

  @Autowired
  public TestService(AppUserRepository appUserRepository, BidRepository bidRepository,
                     AuctionRepository auctionRepository,
                     TreasurySecurityRepository treasurySecurityRepository, AuctionSeeder auctionSeeder) {
    this.appUserRepository = appUserRepository;
    this.bidRepository = bidRepository;
    this.auctionRepository = auctionRepository;
    this.treasurySecurityRepository = treasurySecurityRepository;

    // SEEDERS
    this.auctionSeeder = auctionSeeder;
  }

  public void fillDatabase() {

    Bid bid1 = new Bid();
    bid1.setCompetitive(true);
    bid1.setAmount(300);
    bid1.setRate(1.1f);
    bid1.setAccepted(false);
    bid1.setArchived(false);

    Bid bid2 = new Bid();
    bid2.setCompetitive(true);
    bid2.setAmount(300);
    bid2.setRate(1.0f);
    bid2.setAccepted(false);
    bid2.setArchived(false);

    Bid bid3 = new Bid();
    bid3.setCompetitive(true);
    bid3.setAmount(300);
    bid3.setRate(1.0f);
    bid3.setAccepted(false);
    bid3.setArchived(false);

    Bid bid4 = new Bid();
    bid4.setCompetitive(false);
    bid4.setAmount(600);

    Bid bid5 = new Bid();
    bid5.setCompetitive(false);
    bid5.setAmount(400);

    Bid bid6 = new Bid();
    bid6.setCompetitive(false);
    bid6.setAmount(600);

    TreasurySecurity treasurySecurity1 = new TreasurySecurity();
    treasurySecurity1.setSecurityName("30-year T-Bond");
    treasurySecurity1.setSecurityType("T-Bond");
    treasurySecurity1.setSecurityTerm("30-year");
    treasurySecurity1.setTotalAmount(1_500);
    treasurySecurity1.setIssueDate(LocalDate.now().plusWeeks(1));
    treasurySecurity1.setMaturityDate(LocalDate.now().plusWeeks(1).plusYears(30));
//		treasurySecurity1.setHighRate(9.9f);

    TreasurySecurity treasurySecurity2 = new TreasurySecurity();
    treasurySecurity2.setSecurityName("26-week T-Bill");
    treasurySecurity2.setSecurityType("T-Bill");
    treasurySecurity2.setSecurityTerm("26-week");
    treasurySecurity2.setTotalAmount(55_000_000);
    treasurySecurity2.setIssueDate(LocalDate.now().plusDays(10));
    treasurySecurity2.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));
//		treasurySecurity2.setHighRate(8.4f);

    TreasurySecurity treasurySecurity3 = new TreasurySecurity();
    treasurySecurity3.setSecurityName("10-year T-Note");
    treasurySecurity3.setSecurityType("T-Note");
    treasurySecurity3.setSecurityTerm("10-year");
    treasurySecurity3.setTotalAmount(220_000_000);
    treasurySecurity3.setIssueDate(LocalDate.now().plusWeeks(2));
    treasurySecurity3.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));

    TreasurySecurity treasurySecurity4 = new TreasurySecurity();
    treasurySecurity4.setSecurityName("5-year T-Note");
    treasurySecurity4.setSecurityType("T-Note");
    treasurySecurity4.setSecurityTerm("5-year");
    treasurySecurity4.setTotalAmount(220_000_000);
    treasurySecurity4.setIssueDate(LocalDate.now().plusWeeks(2));
    treasurySecurity4.setMaturityDate(LocalDate.now().plusWeeks(2).plusYears(5));
//		treasurySecurity3.setHighRate(11.2f);
    TreasurySecurity treasurySecurity5 = new TreasurySecurity();
    treasurySecurity5.setSecurityName("26-week T-Bill");
    treasurySecurity5.setSecurityType("T-Bill");
    treasurySecurity5.setSecurityTerm("26-week");
    treasurySecurity5.setTotalAmount(55_000_000);
    treasurySecurity5.setIssueDate(LocalDate.now().plusDays(10));
    treasurySecurity5.setMaturityDate(LocalDate.now().plusDays(10).plusWeeks(26));

    AppUser appUser1 = new AppUser();
    appUser1.setUsername("ablak_aladar");
    appUser1.setEmail("ablak.aladar@gmail.com");
    //pass: asdfASDF8+
    appUser1.setPassword("$2a$10$0fIcc44WP/h3eV7gSU1NKupoN.JId9A1OAJY/jjW.ZjUmfKcG//sa");
    appUser1.setInstitution("Morgan Stanley");
    appUser1.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
    appUser1.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
    appUser1.setAdmin(true);
    appUser1.setActivated(true);
    appUser1.setApproved(true);
    appUser1.setDisabled(false);
    // appUser1.setReactivationToken("a_reactivationtoken");
    // appUser1.setReactivationTokenExpiration(LocalDateTime.now());


    AppUser appUser2 = new AppUser();
    appUser2.setUsername("bejarat_bela");
    appUser2.setEmail("bejarat.aladar@gmail.com");
    //pass: asdfASDF8+
    appUser1.setPassword("$2a$10$g3qAl4yAlVWp4VWGOHHfMudFYSEl4cuevaXm.KjJGDwYERJrKybWK");
    appUser2.setInstitution("Morgan Stanley");
    appUser2.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
    appUser2.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
    appUser2.setAdmin(false);
    appUser2.setActivated(true);
    appUser2.setApproved(true);
    appUser2.setDisabled(false);



    // appUser2.setReactivationToken("a_reactivationtoken");
    // appUser2.setReactivationTokenExpiration(LocalDateTime.now());

    //finished
    Auction auction1 = new Auction();
    auction1.setAuctionStartDate(LocalDateTime.now());
    auction1.setAuctionEndDate(LocalDateTime.now());
    auction1.setProcessed(false);
    auction1.addTreasurySecurity(treasurySecurity1);
    auction1.addTreasurySecurity(treasurySecurity2);
    auction1.addTreasurySecurity(treasurySecurity3);
    appUser1.setDisabled(false);

    //ongoing
    Auction auction2 = new Auction();
    auction2.setAuctionStartDate(LocalDateTime.now());
    auction2.setAuctionEndDate(LocalDateTime.now().plusDays(10));
    auction2.addTreasurySecurity(treasurySecurity4);
    auction2.setProcessed(false);
    auction2.setDisabled(false);


    //upcoming
    Auction auction3 = new Auction();
    auction3.setAuctionStartDate(LocalDateTime.now().plusDays(1));
    auction3.setAuctionEndDate(LocalDateTime.now().plusDays(2));
    auction3.addTreasurySecurity(treasurySecurity5);
    auction3.setProcessed(false);
    auction3.setDisabled(false);

    appUserRepository.save(appUser1);
    appUserRepository.save(appUser2);
    auctionRepository.save(auction1);
    auctionRepository.save(auction2);
    auctionRepository.save(auction3);
    treasurySecurityRepository.save(treasurySecurity1);
    treasurySecurityRepository.save(treasurySecurity2);
    treasurySecurityRepository.save(treasurySecurity3);
    treasurySecurityRepository.save(treasurySecurity4);
    treasurySecurityRepository.save(treasurySecurity5);

    // save user 1 & 2
    appUserRepository.save(appUser1);
    appUserRepository.save(appUser2);

    // save auction and security
    auctionRepository.save(auction1);
    treasurySecurityRepository.save(treasurySecurity1);
    treasurySecurityRepository.save(treasurySecurity2);
    treasurySecurityRepository.save(treasurySecurity3);

    // add bid to security (sec has many bids)
    treasurySecurity1.addBid(bid1);
    treasurySecurity1.addBid(bid2);
    treasurySecurity1.addBid(bid3);
    treasurySecurity1.addBid(bid4);
    treasurySecurity1.addBid(bid5);
    treasurySecurity1.addBid(bid6);

    // add bid to user (user has many bids)
    appUser1.adBid(bid1);
    appUser1.adBid(bid2);
    appUser1.adBid(bid3);
    appUser1.adBid(bid4);
    appUser1.adBid(bid5);

    // save bid
    bidRepository.save(bid1);
    bidRepository.save(bid2);
    bidRepository.save(bid3);
    bidRepository.save(bid4);
    bidRepository.save(bid5);
    bidRepository.save(bid6);


  }

  public void seedDatabase() {
    auctionSeeder.saveAuctions();
  }
}
