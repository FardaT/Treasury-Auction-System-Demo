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

import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
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
        appUser1.setUsername("ablak_aladar");
        appUser1.setEmail("ablak.aladar@gmail.com");
        appUser1.setPassword("123");
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
        appUser2.setPassword("123");
        appUser2.setInstitution("Morgan Stanley");
        appUser2.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        appUser2.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        appUser2.setAdmin(true);
        appUser2.setActivated(true);
        appUser2.setApproved(false);
        appUser2.setDisabled(true);
        // appUser2.setReactivationToken("a_reactivationtoken");
        // appUser2.setReactivationTokenExpiration(LocalDateTime.now());

        Auction auction1 = new Auction();
        auction1.setAuctionStartDate(LocalDateTime.now());
        auction1.setAuctionEndDate(LocalDateTime.now());
        auction1.setProcessed(true);
        auction1.addTreasurySecurity(treasurySecurity1);

        // save user 1 & 2
        appUserRepository.save(appUser1);
        appUserRepository.save(appUser2);

        // save auction and security
        auctionRepository.save(auction1);
        treasurySecurityRepository.save(treasurySecurity1);

        // add bid to security (sec has many bids)
        treasurySecurity1.addBid(bid1);

        // add bid to user (user has many bids)
        appUser1.addBid(bid1);

        // save bid
        bidRepository.save(bid1);
    }
}
