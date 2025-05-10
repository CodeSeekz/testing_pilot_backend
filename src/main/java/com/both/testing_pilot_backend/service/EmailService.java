package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.event.ForgetPasswordEvent;
import com.both.testing_pilot_backend.event.UserRegistrationEvent;

import java.util.Map;

public interface EmailService {
    void sendRegistrationVerification(UserRegistrationEvent payload);

    void sendForgetPasswordRequest(ForgetPasswordEvent payload);

    /**
     * More generic method for sending emails using a template and a map of variables.
     *
     * @param to           Recipient email address
     * @param subjectKey   MessageSource key for the subject line
     * @param templateName Thymeleaf template name (without .html extension)
     * @param variables    Map containing variables for the template context
     */
    void sendTemplatedEmail(String to, String subjectKey, String templateName, Map<String, Object> variables);
}
