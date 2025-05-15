package com.both.testing_pilot_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "github")
public class GithubConfig {
    private String api;
    private String userApi;
    private String clientId;
    private String clientSecret;
}
