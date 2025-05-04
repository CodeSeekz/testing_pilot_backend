package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.model.entity.VerificationToken;
import com.both.testing_pilot_backend.repository.VerificationTokenRepository;
import com.both.testing_pilot_backend.service.VerificationTokenService;
import org.springframework.stereotype.Service;


@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken createToken(String email, String token) {
       return  verificationTokenRepository.saveEmailToken(new VerificationToken(email, token));
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenRepository.getByToken(token);
    }

    @Override
    public void removeByTokenAndEmail(String token, String email) {
        verificationTokenRepository.removeByTokenAndEmail(token, email);
    }
}
