package com.greenfox.treasuryauctionsystem.controllers.rest_controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

	@GetMapping({"/public"})
	public String publicEndpoint () {
		return "Public Endpoint Response";
	}

	@GetMapping({"/protected"})
	public String protectedEndpoint () {
		return "Protected Endpoint Response";
	}
}
