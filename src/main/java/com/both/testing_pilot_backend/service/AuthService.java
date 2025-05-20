package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.dto.request.RegisterRequestDTO;
import com.both.testing_pilot_backend.dto.response.AuthResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {
    void register(RegisterRequestDTO requestDTO);

    void resendEmailVerification(String email);

    void verifyEmail(String email, String plainOtp);

    void requestForgetPassword(String email);

    void resetPassword(String email, String plainOtp, String newPassword);

    AuthResponse googleOauthCallback(String googleToken) throws GeneralSecurityException, IOException;

    AuthResponse gitOauthLogin(String githubCode);
}
