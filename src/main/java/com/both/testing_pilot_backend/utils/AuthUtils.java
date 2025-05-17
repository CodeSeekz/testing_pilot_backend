package com.both.testing_pilot_backend.utils;

import com.both.testing_pilot_backend.model.User;
import com.both.testing_pilot_backend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final UserServiceImpl userService;

    public User getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return userService.getUserByEmail(userDetails.getUsername());
    }
}
