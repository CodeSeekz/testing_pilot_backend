package com.both.testing_pilot_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OtpCode {
    private UUID id;
    private String hashOtp;
    private User user;
    private LocalDateTime expireDate;

    public OtpCode(User user, String hashedOtp) {
        this.user = user;
        this.hashOtp = hashedOtp;
        this.expireDate = LocalDateTime.now().plusMinutes(10);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireDate);
    }
}
