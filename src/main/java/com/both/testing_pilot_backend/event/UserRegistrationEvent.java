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
    private String verifyToken;
    private String url;

    public UserRegistrationEvent(Object source, String email, String name, String resetToken, String url) {
        super(source);
        this.email = email;
        this.name = name;
        this.verifyToken = resetToken;
        this.url = url;
    }
}
