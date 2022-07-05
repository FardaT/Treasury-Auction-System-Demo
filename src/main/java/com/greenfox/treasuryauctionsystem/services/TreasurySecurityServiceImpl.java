package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasurySecurityServiceImpl implements TreasurySecurityService {

    // DI
    private final TreasurySecurityRepository treasurySecurityRepository;

    public TreasurySecurityServiceImpl(TreasurySecurityRepository treasurySecurityRepository) {
        this.treasurySecurityRepository = treasurySecurityRepository;
    }

    // READ - find all
    @Override
    public List<TreasurySecurity> findAllSecurities() {
        return treasurySecurityRepository.findAll();
    }
}
