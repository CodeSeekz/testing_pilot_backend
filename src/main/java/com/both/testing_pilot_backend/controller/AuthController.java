package com.both.testing_pilot_backend.controller;

import com.both.testing_pilot_backend.jwt.JwtService;
import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.*;
import com.both.testing_pilot_backend.model.response.AuthResponse;
import com.both.testing_pilot_backend.service.AuthService;
import com.both.testing_pilot_backend.service.UserService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws Exception {
        final User user = userService.getUserByEmail(request.getEmail());

        if (user.getIsVerified() == false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email has not been verified yet. Please verify your email and try again.");
        }

        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        final String token = jwtService.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PostMapping("/verification/resend")
    public ResponseEntity<?> resendEmailVerification(@RequestBody EmailVerificationResendRequest request) {
        authService.resendEmailVerification(request.getEmail());

        return ResponseEntity.ok("New verification has been send to your email " + request.getEmail());
    }

    @PostMapping("/verification/verify")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody EmailVerificationRequest request) {
        authService.verifyEmail(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("Email has been successfully verified");
    }


    //	{
    //		"email": "user@example.com"
    //	}
    @PostMapping("/password/request-reset-otp")
    public ResponseEntity<?> sendForgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        authService.requestForgetPassword(request.getEmail());
        return ResponseEntity.ok("If an account with that email exists, a reset link has been sent.");
    }

    @PostMapping("/password/reset-with-otp")
    public ResponseEntity<?> verifyForgetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.ok("Password has been successfully reset.");
    }

    @PostMapping("/password/resend-reset-otp")
    public ResponseEntity<?> resendResetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        authService.requestForgetPassword(request.getEmail());
        return ResponseEntity.ok("If an account with that email exists, a reset link has been sent.");
    }

}
