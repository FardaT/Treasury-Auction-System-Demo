package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.IllegalArgumentException;
import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

	// DI
	private final AppUserRepository appUserRepository;
	private final EmailService emailService;

	@Autowired
	public AppUserServiceImpl (AppUserRepository appUserRepository, EmailService emailService) {
		this.appUserRepository = appUserRepository;
		this.emailService = emailService;
	}

	// STORE
	@Override
	public Map<String, String> registerAppUser (
			AppUser appUser,
			String confirmpassword) throws MessagingException {

		Map<String, String> errors = new HashMap<>();

		// unique
		AppUser userByUsername = appUserRepository.findByUsername(appUser.getUsername());
		if (userByUsername != null) {
			errors.put("UNIQUE_USERNAME", "Username is already in use!");
		}
		AppUser userByEmail = appUserRepository.findAppUserByEmail(appUser.getEmail());
		if (userByEmail != null) {
			errors.put("UNIQUE_EMAIL", "Email is already in use!");
		}

		// required
		if (appUser.getUsername().equals("")) {
			errors.put("REQUIRED_USERNAME", "Username field is required!");
		}
		if (appUser.getEmail().equals("")) {
			errors.put("REQUIRED_EMAIL", "Email field is required!");
		}
		if (appUser.getInstitution().equals("")) {
			errors.put("REQUIRED_INSTITUTION", "Institution field is required!");
		}
		if (appUser.getPassword().equals("")) {
			errors.put("REQUIRED_PASSWORD", "Password field is required!");
		}
		if (confirmpassword.equals("")) {
			errors.put("REQUIRED_PASSWORD_CONFIRM", "Password confirm field is required!");
		}

		// regex
		if (!Utility.validatePassword(appUser.getPassword())) {
			errors.put("REGEX_PASSWORD", "Password regex failed!");
		}

		// confirm password
		if (!appUser.getPassword().equals(confirmpassword)) {
			errors.put("PASSWORDS_DONT_MATCH", "The passwords don't match");
		}

		if (!errors.isEmpty()) {
			return errors;
		} else {
			// if validation is ok, then save user
			appUserRepository.save(appUser);

			// send confirm email with token
			emailService.sendHtmlMessage(
					appUser.getEmail(),
					"Successfull registration",
					Utility.setConfirmationEmailText(appUser.getUsername(),
							appUser.getActivationToken()));

			return errors;
		}
	}

	// ACTIVATE ACCOUNT BY TOKEN
	@Override
	public Map<String, String> activateAccount (String activationToken) {

		Map<String, String> errors = new HashMap<>();

		// we need to get the user by the token from the email
		AppUser appUser = appUserRepository.findByActivationToken(activationToken);

		// if user is not found
		if (appUser == null) {

			errors.put("INVALID_TOKEN", "Please use a valid token");
			return errors;

			// if token is expired
		} else if (appUser.getActivationTokenExpiration().isBefore(LocalDateTime.now())) {

			// TODO send new email
			errors.put("TOKEN_EXPIRED", "Your token expired.");
			return errors;

		} else {

			// if everything is ok, then activate acc and save
			appUser.setActivated(true);
			appUserRepository.save(appUser);
			return errors;
		}
	}

	@Override
	public AppUser findUserByUsername (String userName) {
		return appUserRepository.findAppUserByUsername(userName);
	}

	@Override
	public AppUser findUserByEmail (ForgottenPasswordEmailInput emailInput) {
		return appUserRepository.findAppUserByEmail(emailInput.getEmail());
	}

	@Override
	public String saveToken (AppUser appUser) {
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
	public AppUser validateToken (String token) {
		return appUserRepository.findAppUserByReactivationToken(token);
	}

	@Override
	public Map<String, String> saveNewPassword (PasswordReset passwordReset) {

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

	//Authentication details based on username or email
	@Override
	public UserDetails loadUserByUsername (String loginDetail) throws UsernameNotFoundException {
		if (loginDetail == null) {
			throw new IllegalArgumentException("Username or email cannot be null.");
		}
		AppUser appUser = appUserRepository.findByUsernameOrEmail(loginDetail, loginDetail);
		if (appUser == null) {
			throw new UsernameNotFoundException(
					"No username or email can be found in the database");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(appUser.isAdmin() ? "ADMIN" : "USER"));
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}
}
