package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.services.AppUserService;

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
}
