package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;

@Service
public interface AppUserService {

	// STORE
	Map<String, String> registerAppUser (AppUser appUser, String confirmpassword) throws MessagingException;

	// ACTIVATE ACCOUNT BY TOKEN
	Map<String, String> activateAccount (String activationToken);

	AppUser findUserByUsername (String userName);

	AppUser findUserByEmail (ForgottenPasswordEmailInput emailInput);

	String saveToken (AppUser appUser);

	AppUser validateToken (String token);

	Map<String, String> saveNewPassword (PasswordReset passwordReset);
}
