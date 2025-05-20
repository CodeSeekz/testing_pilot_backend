package com.both.testing_pilot_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubUserEmail {
    private String email;
    private boolean primary;
    private boolean verified;
}
