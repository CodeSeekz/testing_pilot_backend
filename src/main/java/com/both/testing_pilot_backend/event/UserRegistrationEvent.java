package com.both.testing_pilot_backend.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private String email;
    private String name;
    private String otpCode;


    public UserRegistrationEvent(Object source, String email, String name, String otpCode) {
        super(source);
        this.email = email;
        this.name = name;
        this.otpCode =  otpCode;
    }
}
