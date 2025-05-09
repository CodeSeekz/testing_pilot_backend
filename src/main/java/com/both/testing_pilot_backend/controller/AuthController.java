package com.both.testing_pilot_backend.controller;

import com.both.testing_pilot_backend.jwt.JwtService;
import com.both.testing_pilot_backend.model.request.AuthRequest;
import com.both.testing_pilot_backend.model.response.AuthResponse;
import com.both.testing_pilot_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws Exception {
		authenticate(request.getEmail(), request.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
		final String token = jwtService.generateToken(userDetails);
		AuthResponse authResponse = new AuthResponse(token);
		return ResponseEntity.ok(authResponse);
	}


}
