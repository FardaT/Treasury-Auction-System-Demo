package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {

    // STORE
    void saveAppUser(AppUser appUser);

    // ACTIVATE ACCOUNT BY TOKEN
    void activateAccount(String activationToken);
}
