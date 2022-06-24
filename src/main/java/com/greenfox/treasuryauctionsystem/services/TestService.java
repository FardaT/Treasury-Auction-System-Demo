package com.greenfox.treasuryauctionsystem.services;


import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
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


  @Autowired
  public TestService(AppUserRepository appUserRepository, BidRepository bidRepository,
                     AuctionRepository auctionRepository,
                     TreasurySecurityRepository treasurySecurityRepository) {
    this.appUserRepository = appUserRepository;
    this.bidRepository = bidRepository;
    this.auctionRepository = auctionRepository;
    this.treasurySecurityRepository = treasurySecurityRepository;
  }

  public void fillDatabase() {

    Bid bid1 = new Bid();
    bid1.setCompetitive(true);
    bid1.setAmount(1111111);
    bid1.setRate(1.1f);
    bid1.setAccepted(true);
    bid1.setArchived(true);

    TreasurySecurity treasurySecurity1 = new TreasurySecurity();
    treasurySecurity1.setSecurityName("Long Bond 1");
    treasurySecurity1.setSecurityType("Bond");
    treasurySecurity1.setSecurityTerm("2032-06-22");
    treasurySecurity1.setTotalAmount(555);
    treasurySecurity1.setIssueDate(LocalDate.now());
    treasurySecurity1.setMaturityDate(LocalDate.now());
    treasurySecurity1.setHighRate(9.9f);

    AppUser appUser1 = new AppUser();
    appUser1.setUsername("a_user");
    appUser1.setPassword("a_password");
    appUser1.setEmail("a@a.a");
    appUser1.setAdmin(true);
    appUser1.setInstitution("A");
    appUser1.setActivationToken("a_token");
    appUser1.setActivationTokenExpiration(LocalDateTime.now());
    appUser1.setActivated(false);
    appUser1.setReactivationToken("a_reactivationtoken");
    appUser1.setReactivationTokenExpiration(LocalDateTime.now());
    appUser1.addBid(bid1);

    AppUser appUser2 = new AppUser();
    appUser2.setUsername("szecsiistvan");
    appUser2.setPassword("password");
    appUser2.setEmail("szecsi.istvan@gmail.com");
    appUser2.setAdmin(true);
    appUser2.setInstitution("Mighty Rooster Team");
//    appUser2.setActivationToken(null);
//    appUser2.setActivationTokenExpiration(null);
    appUser2.setActivated(true);
//    appUser2.setReactivationToken(null);
//    appUser2.setReactivationTokenExpiration(null);
//    appUser2.addBid(null);

    Auction auction1 = new Auction();
    auction1.setAuctionStartDate(LocalDateTime.now());
    auction1.setAuctionEndDate(LocalDateTime.now());
    auction1.setProcessed(true);
    auction1.addBid(bid1);
    auction1.addTreasurySecurity(treasurySecurity1);

    appUserRepository.save(appUser1);
    appUserRepository.save(appUser2);
    auctionRepository.save(auction1);
    bidRepository.save(bid1);
    treasurySecurityRepository.save(treasurySecurity1);
  }
}
