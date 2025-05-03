package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.event.UserRegistrationEvent;

public interface EmailService {
    void sendRegistrationVerification(UserRegistrationEvent payload);
}
