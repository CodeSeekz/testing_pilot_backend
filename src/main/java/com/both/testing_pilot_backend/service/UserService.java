package com.both.testing_pilot_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    void updateIsVerified(boolean isVerified);
}
