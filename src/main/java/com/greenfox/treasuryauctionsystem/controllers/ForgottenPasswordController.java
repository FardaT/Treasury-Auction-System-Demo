package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.ForgottenPasswordEmailInput;
import com.greenfox.treasuryauctionsystem.models.dtos.PasswordReset;
import com.greenfox.treasuryauctionsystem.services.UserServiceForPasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/resetpassword")
public class ForgottenPasswordController {

  private UserServiceForPasswordReset userService;

  @Autowired
  public ForgottenPasswordController(UserServiceForPasswordReset userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String showResetPasswordForm() {
    return "resetpasswordform";
  }

  @PostMapping
  public String getEmailForPasswordReset(@ModelAttribute ForgottenPasswordEmailInput emailInput,
                                         Model model) {

    AppUser user = userService.findUserByEmail(emailInput);

    if (user == null) {
      // TODO: 2022. 06. 24. redirects to email not found page
      model.addAttribute("message", "Email not found.");
      return "resetmessagepage";
    } else {
      userService.saveToken(user);
      model.addAttribute("message", "Email was sent to you.");
      return "resetmessagepage";
    }
  }

  @GetMapping("/reset")
  public String showResetForm(@RequestParam String token, Model model) {
    System.out.println("token: " + token);
    AppUser user = userService.validateToken(token);
    System.out.println(user);


    if (user == null) {
      model.addAttribute("message", "Invalid reset token");
      return "resetmessagepage";
    } else {
      model.addAttribute("token", token);
      return "emailformforpasswordreset";
    }
  }

  @PostMapping("/newpassword")
  public String saveNewPassword(@ModelAttribute PasswordReset passwordReset, Model model) {

    String saveResult = userService.saveNewPassword(passwordReset);

    if (saveResult == null) {
      return "redirect:/";
    } else if(saveResult.equals("INVALID_TOKEN")){
      model.addAttribute("message", "Invalid token");
      return "resetmessagepage";
    } else if (saveResult.equals("PASSWORDS_DONT_MATCH")) {

      System.out.println("PASSWORDS_DONT_MATCH");
    } else if (saveResult.equals("ENTER_MORE_SECURE_PASSWORD")) {
      System.out.println("ENTER_MORE_SECURE_PASSWORD");
    } else {

    }


    return "redirect:/";

  }

}
