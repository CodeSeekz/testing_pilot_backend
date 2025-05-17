package com.both.testing_pilot_backend.event;

import com.both.testing_pilot_backend.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ForgetPasswordEvent extends ApplicationEvent {
    private User user;
    private String otpCode;

    public ForgetPasswordEvent(Object source, User user, String otpCode) {
        super(source);
        this.user = user;
        this.otpCode = otpCode;
    }
}
