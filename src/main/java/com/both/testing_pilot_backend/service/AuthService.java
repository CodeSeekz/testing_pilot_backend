package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;

public interface AuthService {
    void register(RegisterRequestDTO requestDTO);
}
