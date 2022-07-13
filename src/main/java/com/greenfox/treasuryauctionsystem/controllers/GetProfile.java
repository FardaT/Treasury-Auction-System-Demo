package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.BidResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import java.security.Principal;
import java.util.List;

import com.greenfox.treasuryauctionsystem.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping("/profile")
	public String getProfile (Principal principal, Model model) {
		AppUser currentUser = appUserService.getUserByUsername(principal.getName());
		//List<BidResponseDTO> allBidsDto = bidService.getAllBidsDto(principal.getName());
		model.addAttribute("user", currentUser);
		//model.addAttribute("bids", allBidsDto);
		model.addAttribute("bids", currentUser.getBids());

		return "/admin/profile";
	}
	@PostMapping("admin/profile/destroy")
	public String destroy(@RequestParam Long bidId) {

		bidService.disableBid(bidId);

		return "redirect:/profile";
	}

}