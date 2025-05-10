package com.both.testing_pilot_backend.model.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
@Data
public class OtoProperties {
    private Long id;
    private String hashedOtp;
    private User user;
    private LocalDateTime expiryDate;
    private OtpPurpose
}
