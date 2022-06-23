package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {
    void saveAppUser(AppUser appUser);
}
