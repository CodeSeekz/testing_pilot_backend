package com.both.testing_pilot_backend.service.impl;


import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import com.both.testing_pilot_backend.repository.UserRepository;
import com.both.testing_pilot_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return  userRepository.getUserByEmail(email);
	}

	@Override
	public void updateIsVerified(boolean isVerified) {
		userRepository.updateIsVerified(isVerified);
	}
}
