package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@Controller
public class AppUserController {

	// DI
	private final AppUserService appUserService;

	@Autowired
	public AppUserController (AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	/**********
	 *
	 *
	 * HOME SECTION
	 *
	 * **********/

	// SHOW REGISTER FORM
	@GetMapping("register")
	public String register () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/auctions";
		}
		return "home/register.html";
	}

	// TELL THE USER TO CHECK HIS EMAIL
	@GetMapping("confirm")
	public String confirm () {
		return "home/confirm.html";
	}

	// ACTIVATE ACCOUNT BY TOKEN
	@GetMapping("confirm_token")
	public String confirm_token (@RequestParam String activationToken, Model model) {

		Map<String, String> saveResultMessage = appUserService.activateAccount(activationToken);

		if (!saveResultMessage.isEmpty()) {
			if (saveResultMessage.containsKey("INVALID_TOKEN")) {
				model.addAttribute("INVALID_TOKEN", saveResultMessage.get("INVALID_TOKEN"));
			}
			if (saveResultMessage.containsKey("TOKEN_EXPIRED")) {
				model.addAttribute("TOKEN_EXPIRED", saveResultMessage.get("TOKEN_EXPIRED"));
			}
			return "home/confirm_token";
		} else {
			return "home/confirm_token";
		}
	}

	// STORE
	@PostMapping("store")
	public String store (
			AppUser appUser,
			RedirectAttributes redirectAttributes,
			@RequestParam String confirmpassword) throws MessagingException {

		// https://stackoverflow.com/questions/41467779/how-to-pass-only-string-in-thymeleaf-form

		// save to db and send confirmation email
		Map<String, String> saveResultMessage = appUserService.registerAppUser(appUser, confirmpassword);

		// validations
		if (!saveResultMessage.isEmpty()) {

			// unique
			if (saveResultMessage.containsKey("UNIQUE_USERNAME")) {
				redirectAttributes.addFlashAttribute("UNIQUE_USERNAME", saveResultMessage.get("UNIQUE_USERNAME"));
			}
			if (saveResultMessage.containsKey("UNIQUE_EMAIL")) {
				redirectAttributes.addFlashAttribute("UNIQUE_EMAIL", saveResultMessage.get("UNIQUE_EMAIL"));
			}

			// required
			if (saveResultMessage.containsKey("REQUIRED_USERNAME")) {
				redirectAttributes.addFlashAttribute("REQUIRED_USERNAME", saveResultMessage.get("REQUIRED_USERNAME"));
			}
			if (saveResultMessage.containsKey("REQUIRED_EMAIL")) {
				redirectAttributes.addFlashAttribute("REQUIRED_EMAIL", saveResultMessage.get("REQUIRED_EMAIL"));
			}
			if (saveResultMessage.containsKey("REQUIRED_INSTITUTION")) {
				redirectAttributes.addFlashAttribute("REQUIRED_INSTITUTION", saveResultMessage.get("REQUIRED_INSTITUTION"));
			}
			if (saveResultMessage.containsKey("REQUIRED_PASSWORD")) {
				redirectAttributes.addFlashAttribute("REQUIRED_PASSWORD", saveResultMessage.get("REQUIRED_PASSWORD"));
			}
			if (saveResultMessage.containsKey("REQUIRED_PASSWORD_CONFIRM")) {
				redirectAttributes.addFlashAttribute("REQUIRED_PASSWORD_CONFIRM", saveResultMessage.get("REQUIRED_PASSWORD_CONFIRM"));
			}

			// regex
			if (saveResultMessage.containsKey("REGEX_PASSWORD")) {
				redirectAttributes.addFlashAttribute("REGEX_PASSWORD", saveResultMessage.get("REGEX_PASSWORD"));
			}

			// confirm password
			redirectAttributes.addFlashAttribute("PASSWORDS_DONT_MATCH", saveResultMessage.get("PASSWORDS_DONT_MATCH"));

			return "redirect:register";
		} else {
			// redirect to page
			return "redirect:confirm";
		}
	}

	@GetMapping("resetpassword")
	public String showResetPasswordForm () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/auctions";
		}
		return "home/resetpasswordform";
	}

	@PostMapping("resetpassword")
	public String getEmailForPasswordReset (@ModelAttribute ForgottenPasswordEmailInput emailInput,
	                                        Model model, RedirectAttributes redirAttrs) {
		AppUser user = appUserService.findUserByEmail(emailInput);

		if (user == null) {
			redirAttrs.addFlashAttribute("error", "No email found.");
			return "redirect:/resetpassword/";
		} else {
			appUserService.saveToken(user);
			model.addAttribute("message", "Email was sent to you.");
			return "home/resetmessagepage";
		}
	}

	@GetMapping("resetpassword/reset")
	public String showResetForm (@RequestParam String token, Model model) {
		AppUser user = appUserService.validateToken(token);

		if (user == null) {
			model.addAttribute("message", "Invalid reset token");
			return "home/resetmessagepage";
		} else {
			model.addAttribute("token", token);
			return "home/emailformforpasswordreset";
		}
	}

	@PostMapping("resetpassword/newpassword")
	public String saveNewPassword (@ModelAttribute PasswordReset passwordReset, Model model,
	                               RedirectAttributes redirAttrs) {

		Map<String, String> saveResultMessage = appUserService.saveNewPassword(passwordReset);
		if (saveResultMessage.containsKey("INVALID_TOKEN")) {
			model.addAttribute("message", saveResultMessage.get("INVALID_TOKEN"));
			return "home/resetmessagepage";
		}
		if (saveResultMessage.containsKey("TOKEN_EXPIRED")) {
			model.addAttribute("message", saveResultMessage.get("TOKEN_EXPIRED"));
			return "home/resetmessagepage";
		}
		if (saveResultMessage.containsKey("PASSWORDS_DONT_MATCH")) {
			redirAttrs.addFlashAttribute("error", saveResultMessage.get("PASSWORDS_DONT_MATCH"));
			model.addAttribute("token", passwordReset.getToken());
			return "redirect:/resetpassword/reset?token=" + passwordReset.getToken();
		}
		if (saveResultMessage.containsKey("ENTER_MORE_SECURE_PASSWORD")) {
			redirAttrs.addFlashAttribute("error", saveResultMessage.get("ENTER_MORE_SECURE_PASSWORD"));
			return "redirect:/resetpassword/reset?token=" + passwordReset.getToken();
		}
		return "redirect:/";
	}

	/**********
	 *
	 *
	 * ADMIN SECTION
	 *
	 * **********/

	// READ - all users
	@GetMapping("admin/users")
	public String read (Model model, Principal principal) {
		// get currently logged in user
		AppUser currentUser = appUserService.getUserByUsername(principal.getName());

		// get all users from db
		List<AppUser> appUsers = appUserService.getAllAppUsers();

		model.addAttribute("appUsers", appUsers);
		model.addAttribute("user", currentUser);

		return "admin/users";
	}

	// UPDATE - approve user reg (isApproved set to TRUE)
	@PostMapping("admin/users/approve")
	public String approve (@RequestParam Long appUserId, RedirectAttributes redirectAttributes) {
		AppUser appUser = appUserService.approveAppUser(appUserId);
		redirectAttributes.addFlashAttribute("flashMessage", "The user with username " + appUser.getUsername() + " got approved!");
		return "redirect:/admin/users";
	}

	// UPDATE - enable user (isDisabled set to FALSE)
	@PostMapping("admin/users/enable")
	public String enable (@RequestParam Long appUserId, RedirectAttributes redirectAttributes) {
		AppUser appUser = appUserService.enableAppUser(appUserId);
		redirectAttributes.addFlashAttribute("flashMessage", "The user with username " + appUser.getUsername() + " got enabled!");
		return "redirect:/admin/users";
	}

	// UPDATE - disable user (isDisabled set to TRUE)
	@PostMapping("admin/users/disable")
	public String disable (@RequestParam Long appUserId, RedirectAttributes redirectAttributes) {
		AppUser appUser = appUserService.disableAppUser(appUserId);
		redirectAttributes.addFlashAttribute("flashMessage", "The user with username " + appUser.getUsername() + " got disabled!");
		return "redirect:/admin/users";
	}
}
