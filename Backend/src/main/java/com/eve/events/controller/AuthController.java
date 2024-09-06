package com.eve.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eve.events.PayLoad.JwtRequest;
import com.eve.events.PayLoad.JwtResponse;
import com.eve.events.entity.Users;
import com.eve.events.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	private final AuthenticationService authService;

	public AuthController(AuthenticationService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<JwtResponse> register(@RequestBody Users request) {
		log.info("Received registration request: {}", request);
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		log.info("Received login request: {}", request);
		return ResponseEntity.ok(authService.login(request));
	}
}
