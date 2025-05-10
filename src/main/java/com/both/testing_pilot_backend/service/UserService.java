package com.both.testing_pilot_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;


public interface UserService extends UserDetailsService {
    void updateIsVerified(UUID userId, boolean isVerified);

    void updatePassword(UUID userId, String newPassword);
}
