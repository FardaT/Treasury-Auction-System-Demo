package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {

    // STORE
    AppUser saveAppUser(AppUser appUser);

    // ACTIVATE ACCOUNT BY TOKEN
    void activateAccount(String activationToken);

    public AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput);

    public String saveToken(AppUser appUser);

    public AppUser validateToken(String token);

    public String saveNewPassword(PasswordReset passwordReset);
}
