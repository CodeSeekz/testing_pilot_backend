package com.both.testing_pilot_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "google")
public class GoogleConfig {
    private String clientId;
}
