package com.greenfox.treasuryauctionsystem.controllers.rest_controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.AppUserDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/protected")
public class ProtectedRestController {

	private final AppUserService appUserService;

	@Autowired
	public ProtectedRestController (AppUserService appUserService) {
		this.appUserService = appUserService;
	}


	@GetMapping("/appusers")
	public List<AppUserDTO> appusers () {
		return appUserService.getAllAppUsersDto();
	}
}
