package com.fl.service.handler.impl;

import com.fl.service.entity.*;
import com.fl.service.handler.UserService;
import com.fl.service.listener.SendEmailVerificationEvent;
import com.fl.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    /*@Autowired
    private UserDetailsRepository userDetailsRepository;*/

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final JavaMailSender javaMailSender;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher, JavaMailSender javaMailSender) {
        this.userMapper = userMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public ServiceAPIResponse registerUser(UserRequest userRequest) {
        try {
            List<User> userNames = userMapper.checkUserNameExists(userRequest.getUserName());
            if (userNames != null && userNames.size() > 0)
                return new ServiceAPIResponse(false, "UserName exists");
            List<User> emails = userMapper.checkEmailExists(userRequest.getEmail());
            if (emails != null && emails.size() > 0)
                return new ServiceAPIResponse(false, "Email exists");
            User user = new User(userRequest.getName(), userRequest.getUserName(),
                    userRequest.getEmail(), userRequest.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.addUser(user);

            try {
                UserRequest emailVerificationUserRequest = new UserRequest();
                emailVerificationUserRequest.setUserName(user.getUserName());
                emailVerificationUserRequest.setEmail(user.getEmail());
                applicationEventPublisher.publishEvent(new SendEmailVerificationEvent(emailVerificationUserRequest));
            } catch (Exception e) {
                e.printStackTrace();
            }

            UserRoles userRoles = new UserRoles();
            System.out.println("a: " + user.getUserId());
            userRoles.setUserId(user.getUserId());
            userRoles.setBasic(true);
            for (String role :
                    userRequest.getRoles()) {
                if (role.equalsIgnoreCase("admin")) userRoles.setAdmin(true);
                if (role.equalsIgnoreCase("roleEditor")) userRoles.setRoleEditor(true);
            }
            userMapper.addRoles(userRoles);
            return new ServiceAPIResponse(true, "User registered successfully");
        } catch (Exception e) {
            return new ServiceAPIResponse(true, "User registration failed");
        }
    }

    public User getUserDetails(String userName) {
        List<User> users = userMapper.getUserByUserName(userName);
        if (users != null && users.size() == 1)
            return users.get(0);
        return null;
    }

    @Override
    public List<User> getAllUserDetails() {
        return userMapper.getAllUsers();
    }

    public ServiceAPIResponse updateUserRoles(String userName, UserRequest userRequest) {
        try {
            Long userId = getUserIdByUserName(userName);
            if (userId == null) return new ServiceAPIResponse(false, "User does not exist");
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(userId);
            for (String role :
                    userRequest.getRoles()) {
                if (role.equalsIgnoreCase("admin")) userRoles.setAdmin(true);
                if (role.equalsIgnoreCase("basic")) userRoles.setBasic(true);
                if (role.equalsIgnoreCase("roleEditor")) userRoles.setRoleEditor(true);
            }
            userMapper.updateUserRoles(userRoles);
            return new ServiceAPIResponse(true, "User roles updated successfully");
        } catch (Exception e) {
            return new ServiceAPIResponse(false, "User roles update failed");
        }
    }

    private Long getUserIdByUserName(String userName) {
        List<User> users = userMapper.getUserByUserName(userName);
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            return user.getUserId();
        }
        return null;
    }

    public ServiceAPIResponse sendEmailVerification(UserRequest userRequest) {
        try {
            String verificationToken = UUID.randomUUID().toString();
            userMapper.addUserVerification(new UserVerification(verificationToken, userRequest.getUserName()));

            String email = userRequest.getEmail();
            String subject = "Confirm your email";
            String message = "Please confirm your email - http://localhost:8080/api/oauth/confirmEmail?token=" + verificationToken;

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
            return new ServiceAPIResponse(true, "Verification email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceAPIResponse(false, "Failed to send verification email");
        }
    }

    public ServiceAPIResponse confirmEmail(String token) {
        try {
            List<UserVerification> userVerifications = userMapper.getUserVerification(token);
            if (userVerifications != null && userVerifications.size() == 1) {
                UserVerification userVerification = userVerifications.get(0);
                userMapper.updateUserStatus(userVerification.getUserName());
            }
            return new ServiceAPIResponse(true, "Email Confirmation successful");
        } catch (Exception e) {
            return new ServiceAPIResponse(false, "Failed to confirm email");
        }
    }
}
