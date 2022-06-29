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

    // STORE
    Map<String, String> registerAppUser(AppUser appUser, String confirmpassword) throws MessagingException;

    // ACTIVATE ACCOUNT BY TOKEN
    Map<String, String> activateAccount(String activationToken);

    AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput);

    String saveToken(AppUser appUser);

    AppUser validateToken(String token);

    Map<String, String> saveNewPassword(PasswordReset passwordReset);

    // READ - all users
    List<AppUser> getAllAppUsers();

    // UPDATE - approve user reg (isApproved set to TRUE)
    void approveAppUser(Long appUserId);

    // UPDATE - enable user (isDisabled set to FALSE)
    void enableAppUser(Long appUserId);

    // UPDATE - disable user (isDisabled set to TRUE)
    void disableAppUser(Long appUserId);
}
