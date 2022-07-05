package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TreasurySecurityService {

    // READ - find all
    List<TreasurySecurity> findAllSecurities();
}
