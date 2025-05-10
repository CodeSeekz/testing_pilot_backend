package com.both.testing_pilot_backend.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationRequest {
    @NotBlank( message = "Email cannot be blank")
    @Email(message = "Email is invalid")
    @Size(max = 255, message = "Email must not be longer that 255 characters")
    private String email;

    @NotNull
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format")
    private String token;
}
