package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.AppUserNotFoundException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalArgumentException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalRegexException;
import com.greenfox.treasuryauctionsystem.exceptions.TokenExpiredException;
import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class AppUserServiceImpl implements AppUserService {

  // DI
  private final AppUserRepository appUserRepository;
  private final EmailService emailService;

  @Autowired
  public AppUserServiceImpl(AppUserRepository appUserRepository, EmailService emailService) {
    this.appUserRepository = appUserRepository;
    this.emailService = emailService;
  }

  // STORE
  @Override
  public void registerAppUser(AppUser appUser) throws MessagingException {

    // check if fields are empty
    if (
        appUser.getUsername().equals("") ||
            appUser.getEmail().equals("") ||
            appUser.getInstitution().equals("") ||
            appUser.getPassword().equals("")) {
      throw new IllegalArgumentException("Registration fields are all required!");
    } else if (!Utility.validatePassword(appUser.getPassword())) {
      // password checking (regex)
      throw new IllegalRegexException("Password regex failed.");
    } else {

      // if validation is ok, then save user
      appUserRepository.save(appUser);

      // send confirm email with token
      emailService.sendHtmlMessage(
              appUser.getEmail(),
              "Successfull registration",
              Utility.setConfirmationEmailText(appUser.getUsername(), appUser.getActivationToken()));
    }
  }

  // ACTIVATE ACCOUNT BY TOKEN
  @Override
  public void activateAccount(String activationToken) {

    // we need to get the user by the token from the email
    AppUser appUser = appUserRepository.findByActivationToken(activationToken);

    // if user is not found
    if (appUser == null) {

      throw new AppUserNotFoundException("There is no user with this token in the db.");

      // if token is expired
    } else if (appUser.getActivationTokenExpiration().isBefore(LocalDateTime.now())) {

      throw new TokenExpiredException("This activationToken is already expired");

    } else {

      // if everything is ok, then activate acc and save
      appUser.setActivated(true);
      appUserRepository.save(appUser);
    }
  }

  @Override
  public AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput) {
    return appUserRepository.findAppUserByEmail(emailInput.getEmail());
  }

  @Override
  public String saveToken(AppUser appUser) {
    String token = PasswordResetTokenGenerator.generatePasswordResetToken();

    appUser.setReactivationToken(token);
    appUser.setReactivationTokenExpiration(LocalDateTime.now().plusDays(1));

    emailService.sendSimpleMessage(appUser.getEmail(), "Reset your password",
        "Dear " + appUser.getUsername() +
            ", please click the link to reset your Treasury Auction Site password: http://localhost:8080/resetpassword/reset?token=" +
            token);
    appUserRepository.save(appUser);
    return token;
  }

  @Override
  public AppUser validateToken(String token) {
    return appUserRepository.findAppUserByReactivationToken(token);
  }

  @Override
  public Map<String, String> saveNewPassword(PasswordReset passwordReset) {

    boolean passwordIsSecure = Utility.validatePassword(passwordReset.getPassword());

    AppUser user = appUserRepository.findAppUserByReactivationToken(passwordReset.getToken());
    boolean isTokenExpired = LocalDateTime.now().isAfter(user.getReactivationTokenExpiration());
    Map<String, String> errors = new HashMap<>();

    if (user == null) {
      errors.put("INVALID_TOKEN", "Please use a valid token");
      return errors;
    }
    if (isTokenExpired) {
      errors.put("TOKEN_EXPIRED", "Your token expired. Please reset your password again.");
      return errors;
    }
    if (!passwordReset.getPassword().equals(passwordReset.getConfirm())) {
      errors.put("PASSWORDS_DONT_MATCH", "The passwords don't match");
      return errors;
    }
    if (!passwordIsSecure) {
      errors.put("ENTER_MORE_SECURE_PASSWORD", "Please enter a more secure password");
      return errors;
    }
    user.setPassword(passwordReset.getPassword());
    user.setReactivationToken(null);
    user.setReactivationTokenExpiration(null);
    appUserRepository.save(user);
    return errors;
  }
}
