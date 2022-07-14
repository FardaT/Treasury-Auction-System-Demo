package com.greenfox.treasuryauctionsystem.controllers.rest_controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.AppUserDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.BidResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping({"/public"})
public class RestApiController {

	private final AppUserService appUserService;
	private final BidService bidService;

	@Autowired
	public RestApiController (AppUserService appUserService, BidService bidService) {
		this.appUserService = appUserService;
		this.bidService = bidService;
	}

	@GetMapping
	public String publicEndpoint () {
		return "Public Endpoint Response";
	}

	@GetMapping("/appusers")
	public List<AppUserDTO> appUsers () {
		return appUserService.getAllAppUsersDto();
	}

	/*@GetMapping("/bids")
	public List<BidResponseDTO> getBids(Principal principal) {
		return bidService.getAllBidsDto(principal.getName());
	}*/
}
