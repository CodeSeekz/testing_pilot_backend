package com.both.testing_pilot_backend.service.impl;


import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.repository.UserRepository;
import com.both.testing_pilot_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(email);
		System.out.println("Loaded user roles: " + user.getRoles()); // Add this line
		return user;
	}
}
