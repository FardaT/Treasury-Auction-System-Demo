package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;

@Controller
public class AppUserController {

    // DI
    private final AppUserService appUserService;
    private final EmailService emailService;

    @Autowired
    public AppUserController(AppUserService appUserService, EmailService emailService) {
        this.appUserService = appUserService;
        this.emailService = emailService;
    }

    // REGISTER PAGE
    @GetMapping("register")
    public String register() {
        return "register.html";
    }

    // CONFIRM page right after reg
    @GetMapping("confirm")
    public String confirm() {
        return "confirm.html";
    }

    // STORE
    @PostMapping("store")
    public String store(AppUser appUser) throws MessagingException {

        // save to db
        appUserService.saveAppUser(appUser);

        // send confirm email with token
        emailService.sendHtmlMessage(
                appUser.getEmail(),
                "Successfull registration",
                Utility.setConfirmationEmailText(appUser.getUsername(), appUser.getActivationToken()));

        // redirect to page
        return "redirect:confirm";
    }
}
