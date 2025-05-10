package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.model.entity.OtpCode;
import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.utils.OtpPurpose;

import java.util.UUID;

public interface OTPService {
    String generateAndPersistOtp(User user);
    boolean validateOTP(String plainOtp, UUID userId);
    OtpCode findByUserId(UUID userId);
}
