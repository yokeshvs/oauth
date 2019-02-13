package com.fl.service.handler.impl;

import com.fl.service.entity.APIResponse;
import com.fl.service.entity.AuthTokenResponse;
import com.fl.service.entity.LoginRequest;
import com.fl.service.entity.ServiceAPIResponse;
import com.fl.service.handler.AuthService;
import com.fl.service.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public APIResponse loginUser(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return new AuthTokenResponse(jwt);
        } catch (BadCredentialsException e) {
            return new ServiceAPIResponse(false, "Invalid Credentials");
        } catch (Exception e) {
            return new ServiceAPIResponse(false, "Login Failed");
        }
    }
}
