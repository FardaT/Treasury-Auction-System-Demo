package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForPasswordReset {


  private AppUserRepository appUserRepository;
  private EmailService emailService;


  @Autowired
  public UserServiceForPasswordReset(AppUserRepository appUserRepository,
                                     EmailService emailService) {
    this.appUserRepository = appUserRepository;
    this.emailService = emailService;
  }

  public AppUser findUserByEmail(ForgottenPasswordEmailInput emailInput) {
    return appUserRepository.findAppUserByEmail(emailInput.getEmail());
  }

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

  public AppUser validateToken(String token) {
    return appUserRepository.findAppUserByReactivationToken(token);
  }

  public String saveNewPassword(PasswordReset passwordReset) {

    // TODO: 2022. 06. 24. validate password strength

    AppUser user = appUserRepository.findAppUserByReactivationToken(passwordReset.getToken());
    if (user==null) {
      return "INVALID_TOKEN";
    } else if (!passwordReset.getPassword().equals(passwordReset.getConfirm())) {
      return "PASSWORDS_DONT_MATCH";
    } else if (false) {
      return "ENTER_MORE_SECURE_PASSWORD";
    } else {

      // TODO: 2022. 06. 24. hash password
      user.setPassword(passwordReset.getPassword());
      user.setReactivationToken(null);
      user.setReactivationTokenExpiration(null);
      appUserRepository.save(user);
      return null;
    }
  }
}
