package com.greenfox.treasuryauctionsystem.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String login(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      return "redirect:/auctions";
    }
    return "home/login";
  }

  @GetMapping("/")
  public String redirectIfLoggedIn(){
    return "redirect:/auctions";
  }

}
