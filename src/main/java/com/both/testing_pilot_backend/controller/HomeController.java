package com.both.testing_pilot_backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/home")
@SecurityRequirement(name = "bearerAuth")
public class HomeController {

	@GetMapping
	public String home() {
		return "Home";
	}

}
