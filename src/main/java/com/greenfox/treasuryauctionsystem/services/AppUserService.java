package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;

@Service
public interface AppUserService {

    /**********
     *
     *
     * HOME SECTION
     *
     * **********/

    // STORE
    Map<String, String> registerAppUser(AppUser appUser, String confirmpassword) throws MessagingException;

    // ACTIVATE ACCOUNT BY TOKEN
    Map<String, String> activateAccount(String activationToken);

    AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput);

    String saveToken(AppUser appUser);

    AppUser validateToken(String token);

    Map<String, String> saveNewPassword(PasswordReset passwordReset);

    /**********
     *
     *
     * ADMIN SECTION
     *
     * **********/

    List<AppUser> getAllAppUsers();
}
