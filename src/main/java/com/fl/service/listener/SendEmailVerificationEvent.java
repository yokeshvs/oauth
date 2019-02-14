package com.fl.service.listener;

import com.fl.service.entity.User;
import com.fl.service.entity.UserRequest;
import org.springframework.context.ApplicationEvent;

public class SendEmailVerificationEvent extends ApplicationEvent {
    private UserRequest userRequest;

    public SendEmailVerificationEvent(UserRequest userRequest) {
        super(userRequest);
        this.userRequest = userRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}
