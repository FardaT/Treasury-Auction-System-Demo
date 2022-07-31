package com.greenfox.treasuryauctionsystem.services;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import com.greenfox.treasuryauctionsystem.exceptions.InvalidAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuctionServiceImplTest_2 {

  @Mock
  private AuctionRepository auctionRepository;
  @Mock
  private TreasurySecurityRepository treasurySecurityRepository;
  @Mock
  private BidRepository bidRepository;
  private AuctionService underTest;
  private AutoCloseable autoCloseable;

  @BeforeEach
  void setUp(){
    autoCloseable = MockitoAnnotations.openMocks(this);
    underTest = new AuctionServiceImpl(auctionRepository,treasurySecurityRepository,bidRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void create_IsEmptyInput_SavedToRepo() {
    //when
    Auction auction = new Auction();
    auction.addTreasurySecurity(new TreasurySecurity());
    underTest.create(new Auction());
    // then
    verify(auctionRepository).save(auction);

  }
  @Test
  void create_IsEmptyInput_throwError() {
    //when
    Auction auction = new Auction();
    underTest.create(auction);
    // then
    verify(auctionRepository).save(new Auction());

    assertThrows(InvalidAuctionException.class, () -> auction.getTreasurySecurityList().isEmpty());
  }

}