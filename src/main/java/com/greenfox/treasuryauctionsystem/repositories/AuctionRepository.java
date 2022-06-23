package com.greenfox.treasuryauctionsystem.repositories;

import com.greenfox.treasuryauctionsystem.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction,Long> {
}
