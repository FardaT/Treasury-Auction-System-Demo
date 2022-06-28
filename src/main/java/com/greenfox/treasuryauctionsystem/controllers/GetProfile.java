package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetProfile {

	private final AppUserService appUserService;

	@Autowired
	public GetProfile (AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	@GetMapping("/profile")
	public String getProfile (Model model){
		model.addAttribute("username",);
		return "profile";
	}

}