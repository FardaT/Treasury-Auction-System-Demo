package com.greenfox.treasuryauctionsystem.repositories;

import com.greenfox.treasuryauctionsystem.models.Auction;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction,Long> {

  List<Auction> findAuctionByAuctionStartDateLessThanAndAuctionEndDateGreaterThan(LocalDateTime now, LocalDateTime now2);
  List<Auction> findAuctionByAuctionStartDateGreaterThan(LocalDateTime now);
}
