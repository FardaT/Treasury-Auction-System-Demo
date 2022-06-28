package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetProfile {

	private final AppUserService appUserService;

	@Autowired
	public GetProfile (AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	@GetMapping("/profile")
	public String getProfile (@RequestParam String userName, Model model){
		AppUser user = appUserService.findUserByUsername(userName);
		model.addAttribute("user", user);
		return "profile";
	}

}