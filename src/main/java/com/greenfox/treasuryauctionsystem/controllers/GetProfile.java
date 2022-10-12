package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import java.security.Principal;


import com.greenfox.treasuryauctionsystem.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GetProfile {

	private final AppUserService appUserService;
	private final BidService bidService;

	@Autowired
	public GetProfile (AppUserService appUserService, BidService bidService) {
		this.appUserService = appUserService;
		this.bidService = bidService;
	}

	//@GetMapping("/admin/profile")
	@GetMapping("profile")
	public String getProfile (Principal principal, Model model) {
		AppUser currentUser = appUserService.getUserByUsername(principal.getName());
		model.addAttribute("user", currentUser);
		model.addAttribute("bids", currentUser.getBids());

		return "admin/profile";
	}
}