package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.services.AppUserService;

import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppUserController {

    // DI
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
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
    public String confirm_token(@RequestParam String activationToken, Model model) {

        Map<String, String> saveResultMessage = appUserService.activateAccount(activationToken);

        if (!saveResultMessage.isEmpty()) {
            if (saveResultMessage.containsKey("INVALID_TOKEN")) {
                model.addAttribute("INVALID_TOKEN", saveResultMessage.get("INVALID_TOKEN"));
            }
            if (saveResultMessage.containsKey("TOKEN_EXPIRED")) {
                model.addAttribute("TOKEN_EXPIRED", saveResultMessage.get("TOKEN_EXPIRED"));
            }
            return "confirm_token";
        } else {
            return "confirm_token";
        }
    }

    // STORE
    @PostMapping("store")
    public String store(
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

    @GetMapping("/resetpassword")
    public String showResetPasswordForm() {
        return "resetpasswordform";
    }

    @PostMapping("/resetpassword")
    public String getEmailForPasswordReset(@ModelAttribute ForgottenPasswordEmailInput emailInput,
                                           Model model, RedirectAttributes redirAttrs) {
        AppUser user = appUserService.findUserByEmail(emailInput);

        if (user == null) {
            redirAttrs.addFlashAttribute("error", "No email found.");
            return "redirect:/resetpassword/";
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
    public String saveNewPassword(@ModelAttribute PasswordReset passwordReset, Model model,
                                  RedirectAttributes redirAttrs) {

        Map<String, String> saveResultMessage = appUserService.saveNewPassword(passwordReset);
        if (saveResultMessage.containsKey("INVALID_TOKEN")) {
            model.addAttribute("message", saveResultMessage.get("INVALID_TOKEN"));
            return "resetmessagepage";
        }
        if (saveResultMessage.containsKey("TOKEN_EXPIRED")) {
            model.addAttribute("message", saveResultMessage.get("TOKEN_EXPIRED"));
            return "resetmessagepage";
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
    public String read(Model model) {

        // get all users from db
        List<AppUser> appUsers = appUserService.getAllAppUsers();

        model.addAttribute("appUsers", appUsers);

        return "admin/users";
    }

    // UPDATE - approve user reg (isApproved set to TRUE)
    @PostMapping("admin/users/approve")
    public String approve(@RequestParam Long appUserId) {
        appUserService.approveAppUser(appUserId);
        return "redirect:/admin/users";
    }

    // UPDATE - enable user (isDisabled set to FALSE)
    @PostMapping("admin/users/enable")
    public String enable(@RequestParam Long appUserId) {
        appUserService.enableAppUser(appUserId);
        return "redirect:/admin/users";
    }

    // UPDATE - disable user (isDisabled set to TRUE)
    @PostMapping("admin/users/disable")
    public String disable(@RequestParam Long appUserId) {
        appUserService.disableAppUser(appUserId);
        return "redirect:/admin/users";
    }
}
