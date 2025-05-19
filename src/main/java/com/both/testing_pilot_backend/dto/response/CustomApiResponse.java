package com.both.testing_pilot_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomApiResponse<T> {
    private String message;
    private HttpStatus status;
    private Boolean success;
    private T data;
}
