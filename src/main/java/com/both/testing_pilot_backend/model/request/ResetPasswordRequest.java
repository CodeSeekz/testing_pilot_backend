package com.both.testing_pilot_backend.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest extends EmailVerificationRequest{

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'<>?,./]).{8,20}$",
            message = "Password must be 8-20 characters long and include upper and lower case letters, a number, and a special character"
    )
    private String newPassword;
}
