package com.both.testing_pilot_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationResendRequest {

    @NotBlank(message =  "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not be longer than 255")
    private String email;
}
