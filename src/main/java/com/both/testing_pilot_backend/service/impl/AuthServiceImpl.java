package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.event.UserRegistrationEvent;
import com.both.testing_pilot_backend.exceptions.EmailAlreadyExistException;
import com.both.testing_pilot_backend.jwt.JwtService;
import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.entity.VerificationToken;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import com.both.testing_pilot_backend.repository.UserRepository;
import com.both.testing_pilot_backend.service.AuthService;
import com.both.testing_pilot_backend.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService verificationTokenService;

    @Value("${app.dev.frontend.url}")
    private String frontendUrl;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ApplicationEventPublisher applicationEventPublisher, VerificationTokenService verificationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.eventPublisher = applicationEventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public void register(RegisterRequestDTO requestDTO) {
        User user = userRepository.getUserByEmail(requestDTO.getEmail());

        if (user == null) {
            userRepository.saveUser(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
            VerificationToken verificationToken = verificationTokenService.createToken(requestDTO.getEmail(), UUID.randomUUID().toString());
            UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(this, requestDTO.getEmail(), requestDTO.getUsername(), verificationToken.getToken(), frontendUrl);
            eventPublisher.publishEvent(userRegistrationEvent);
        } else {
            throw new EmailAlreadyExistException("Email already exist!");
        }
    }
}
