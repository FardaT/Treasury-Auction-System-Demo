package com.greenfox.treasuryauctionsystem.controllers.rest_controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.AppUserDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionApiResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api"})
public class RestApiController {

	private final AppUserService appUserService;
	private final AuctionService auctionService;

	@Autowired
	public RestApiController (AppUserService appUserService, AuctionService auctionService) {
		this.appUserService = appUserService;
		this.auctionService = auctionService;
	}

	@GetMapping
	public String publicEndpoint () {
		return "Public Endpoint Response";
	}

	@GetMapping("/appusers")
	public List<AppUserDTO> appUsers () {
		return appUserService.getAllAppUsersDto();
	}
	@GetMapping("/auctions/upcoming")
	public List<AuctionApiResponseDTO> upcomingAuctions () {
		return auctionService.getAllUpcomingAuctions();
	}

	@GetMapping("/auctions/ongoing")
	public List<AuctionApiResponseDTO> ongoingAuctions () {
		return auctionService.getAllOngoingAuctions();
	}

	@GetMapping("/auctions/finished")
	public List<AuctionApiResponseDTO> finishedAuctions () {
		return auctionService.getAllFinishedAuctions();
	}

}
