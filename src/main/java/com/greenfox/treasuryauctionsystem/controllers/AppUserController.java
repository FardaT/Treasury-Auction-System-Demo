package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

@Controller
public class AppUserController {

    // DI
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // SHOW REGISTER FORM
    @GetMapping("register")
    public String register() {
        return "register.html";
    }

    // TELL THE USER TO CHECK HIS EMAIL
    @GetMapping("confirm")
    public String confirm() {
        return "confirm.html";
    }

    // ACTIVATE ACCOUNT BY TOKEN
    @GetMapping("confirm_token")
    public String confirm_token(@RequestParam String activationToken) {
        appUserService.activateAccount(activationToken);
        return "confirm_token";
    }

    // STORE
    @PostMapping("store")
    public String store(AppUser appUser) throws MessagingException {

        // save to db and send confirmation email
        appUserService.registerAppUser(appUser);

        // redirect to page
        return "redirect:confirm";
    }

    @GetMapping("/resetpassword")
    public String showResetPasswordForm() {
        return "resetpasswordform";
    }

    @PostMapping("/resetpassword")
    public String getEmailForPasswordReset(@ModelAttribute ForgottenPasswordEmailInput emailInput,
                                           Model model) {
        AppUser user = appUserService.findUserByEmail(emailInput);

        if (user == null) {
            model.addAttribute("message", "Email not found.");
            return "resetmessagepage";
        } else {
            appUserService.saveToken(user);
            model.addAttribute("message", "Email was sent to you.");
            return "resetmessagepage";
        }
    }

    @GetMapping("/resetpassword/reset")
    public String showResetForm(@RequestParam String token, Model model) {
        AppUser user = appUserService.validateToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid reset token");
            return "resetmessagepage";
        } else {
            model.addAttribute("token", token);
            return "emailformforpasswordreset";
        }
    }

    @PostMapping("/resetpassword/newpassword")
    public String saveNewPassword(@ModelAttribute PasswordReset passwordReset, Model model) {

        String saveResultMessage = appUserService.saveNewPassword(passwordReset);

        if (saveResultMessage == null) {
            return "redirect:/";
        } else if (saveResultMessage.equals("INVALID_TOKEN")) {
            // TODO: 2022. 06. 24. should print out error message to the user
            model.addAttribute("message", "Invalid token");
            return "resetmessagepage";
        } else if (saveResultMessage.equals("PASSWORDS_DONT_MATCH")) {
            // TODO: 2022. 06. 24. should print out error message to the user
            System.out.println("PASSWORDS_DONT_MATCH");
        } else if (saveResultMessage.equals("ENTER_MORE_SECURE_PASSWORD")) {
            // TODO: 2022. 06. 24. should print out error message to the user
            System.out.println("ENTER_MORE_SECURE_PASSWORD");
        } else {
        }
        return "redirect:/";
    }
}
