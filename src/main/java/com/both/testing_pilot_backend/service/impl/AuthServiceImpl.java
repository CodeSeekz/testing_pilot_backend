package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.event.ForgetPasswordEvent;
import com.both.testing_pilot_backend.event.UserRegistrationEvent;
import com.both.testing_pilot_backend.exceptions.BadRequestException;
import com.both.testing_pilot_backend.exceptions.EmailAlreadyExistException;
import com.both.testing_pilot_backend.exceptions.NotFoundException;
import com.both.testing_pilot_backend.model.entity.OtpCode;
import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import com.both.testing_pilot_backend.repository.UserRepository;
import com.both.testing_pilot_backend.service.AuthService;
import com.both.testing_pilot_backend.service.OTPService;
import com.both.testing_pilot_backend.service.UserService;
import com.both.testing_pilot_backend.utils.OtpPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final OTPService otpService;


    @Override
    public void register(RegisterRequestDTO requestDTO) {
        User user = userRepository.getUserByEmail(requestDTO.getEmail());

        if (user == null) {
            User newUser = userRepository.saveUser(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));


            String plainOtp = otpService.generateAndPersistOtp(newUser);

            UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(this, requestDTO.getEmail(), requestDTO.getUsername(), plainOtp);
            eventPublisher.publishEvent(userRegistrationEvent);
        } else {
            throw new EmailAlreadyExistException("Email already exist!");
        }
    }

    @Override
    public void resendEmailVerification(String email) {

        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (user != null && user.getIsVerified() == true) {
            throw new BadRequestException("Email has already been verified");
        }


        OtpCode otpCode = otpService.findByUserId(user.getUserId());
        if (otpCode != null) {
            otpService.deleteOtp(user.getUserId());
        }


        String plainOtp = otpService.generateAndPersistOtp(user);

        UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(this, user.getEmail(), user.getUsername(), plainOtp);
        eventPublisher.publishEvent(userRegistrationEvent);
    }

    @Override
    public void verifyEmail(String email, String token) {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new NotFoundException("Verification failed: User account not found.");
        }

        if (user.getIsVerified() == true) {
            throw new BadRequestException("This account has already been verified.");
        }

        boolean isOtpValid = otpService.validateOTP(token, user.getUserId());

        if (isOtpValid) {
            userService.updateIsVerified(user.getUserId(), true);
        }
    }

    @Override
    public void requestForgetPassword(String email) {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (user.getIsVerified() == false) {
            throw new BadRequestException("Email has not been verified yet");
        }

        otpService.deleteOtp(user.getUserId());
        String plainOtp = otpService.generateAndPersistOtp(user);

        ForgetPasswordEvent forgetPasswordEvent = new ForgetPasswordEvent(this, user, plainOtp);
        eventPublisher.publishEvent(forgetPasswordEvent);
    }

    @Override
    public void resetPassword(String email, String plainOtp, String newPassword) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }


        OtpCode otpCode = otpService.findByUserId(user.getUserId());
        if (otpCode == null || otpCode.isExpired()) {
            throw new BadRequestException("Invalid or expired OTP. Please request a new one.");
        }

        userService.updatePassword(user.getUserId(), passwordEncoder.encode(newPassword));
        otpService.deleteOtp(user.getUserId());
    }
}
