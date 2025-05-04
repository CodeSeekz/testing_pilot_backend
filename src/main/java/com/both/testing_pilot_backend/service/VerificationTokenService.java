package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.model.entity.VerificationToken;

public interface VerificationTokenService {
    VerificationToken createToken(String email, String token);
    VerificationToken getByToken(String token);
    void removeByTokenAndEmail(String token, String email);
}
