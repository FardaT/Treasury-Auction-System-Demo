package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
public class GetProfile {

	private final AppUserService appUserService;

	@Autowired
	public GetProfile (AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	@GetMapping("/profile")
	public String getProfile (HttpServletRequest request, Model model) {
		AppUser user = appUserService.getUserFromRequest(request);
		model.addAttribute("user", user);
		model.addAttribute("bids", user.getBids());
		model.addAttribute("treasuries",
				user.getBids().stream().map(Bid::getTreasurySecurity).collect(Collectors.toList()));


		return "profile";
	}

}