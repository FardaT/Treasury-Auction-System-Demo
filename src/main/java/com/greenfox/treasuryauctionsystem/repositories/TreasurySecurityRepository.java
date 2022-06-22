package com.greenfox.treasuryauctionsystem.repositories;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreasurySecurityRepository extends JpaRepository<TreasurySecurity,Long> {
}
