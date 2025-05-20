package com.both.testing_pilot_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordRequest {

    @Email
    @NotNull
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 6, max = 255,message = "Email must be between 6 to 255 characters")
    private String email;
}
