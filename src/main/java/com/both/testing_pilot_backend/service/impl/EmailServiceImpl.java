package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.event.UserRegistrationEvent;
import com.both.testing_pilot_backend.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendRegistrationVerification(UserRegistrationEvent payload) {
        System.out.println("sending email" + payload.getEmail() + payload.getVerifyToken());
    }
}
