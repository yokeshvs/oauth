package com.fl.service.handler;

import com.fl.service.entity.APIResponse;
import com.fl.service.entity.LoginRequest;

public interface AuthService {
    APIResponse loginUser(LoginRequest loginRequest);
}
