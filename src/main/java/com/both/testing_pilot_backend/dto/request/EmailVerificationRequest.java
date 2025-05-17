package com.both.testing_pilot_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationRequest {
    @NotBlank( message = "Email cannot be blank")
    @Email(message = "Email is invalid")
    @Size(max = 255, message = "Email must not be longer that 255 characters")
    private String email;

    @NotNull
    @Size(min = 6, max =6 , message = "otp must be 6 characters" )
    @Size()
    private String otp;
}
