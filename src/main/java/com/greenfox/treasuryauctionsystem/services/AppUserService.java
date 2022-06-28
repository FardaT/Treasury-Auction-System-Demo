package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface AppUserService {

    // STORE
    Map<String, String> registerAppUser(AppUser appUser, String confirmpassword) throws MessagingException;

    // ACTIVATE ACCOUNT BY TOKEN
    Map<String, String> activateAccount(String activationToken);

    public AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput);

    public String saveToken(AppUser appUser);

    public AppUser validateToken(String token);

    public Map<String, String> saveNewPassword(PasswordReset passwordReset);
}
