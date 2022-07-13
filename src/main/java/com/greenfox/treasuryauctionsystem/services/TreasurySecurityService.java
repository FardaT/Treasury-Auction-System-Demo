package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.TreasurySecurityResponseDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TreasurySecurityService {

  List<TreasurySecurityResponseDTO> getTreasurySecurities(boolean isOngoing, String sortBy, String order);

}
