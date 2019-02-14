package com.fl.service.listener;

import com.fl.service.handler.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class RegistrationEventListener implements ApplicationListener<SendEmailVerificationEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(SendEmailVerificationEvent sendEmailVerificationEvent) {
        userService.sendEmailVerification(sendEmailVerificationEvent.getUserRequest());
    }
}
