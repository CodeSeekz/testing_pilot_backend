package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.event.UserRegistrationEvent;
import com.both.testing_pilot_backend.exceptions.EmailAlreadyExistException;
import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import com.both.testing_pilot_backend.repository.UserRepository;
import com.both.testing_pilot_backend.service.AuthService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void register(RegisterRequestDTO requestDTO) {
        UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(this, requestDTO.getEmail(), requestDTO.getUsername(), "Gay Token", "fghjkl");

        eventPublisher.publishEvent(userRegistrationEvent);

        //        User user = userRepository.getUserByEmail(requestDTO.getEmail());
//
//        if (user == null) {
//            userRepository.saveUser(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
//        } else {
//            throw new EmailAlreadyExistException("Email already exist!");
//        }
    }
}
