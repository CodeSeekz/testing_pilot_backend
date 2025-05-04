package com.both.testing_pilot_backend.model.entity;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VerificationToken {
    private String email;
    private String token;
    private LocalDateTime expireAt;

    private final int expiredTimeInMinutes = 60 * 24 * 7;

    public VerificationToken (String email, String token) {
        this.email = email;
        this.token = token;
        this.expireAt = calculateExpiryDate(expiredTimeInMinutes);
    }

    private LocalDateTime calculateExpiryDate(int expiredTimeInMinutes) {
        return LocalDateTime.now().plusMinutes(expiredTimeInMinutes);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireAt);
    }
}
