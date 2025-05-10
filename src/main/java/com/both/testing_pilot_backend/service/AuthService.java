package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;

public interface AuthService {
    void register(RegisterRequestDTO requestDTO);

    void resendEmailVerification(String email);

    void verifyEmail(String email, String plainOtp);

    void requestForgetPassword(String email);

    void resetPassword(String email, String plainOtp, String newPassword);
}
