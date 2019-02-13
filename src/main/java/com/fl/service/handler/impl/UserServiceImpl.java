package com.fl.service.handler.impl;

import com.fl.service.entity.ServiceAPIResponse;
import com.fl.service.entity.User;
import com.fl.service.entity.UserRequest;
import com.fl.service.entity.UserRoles;
import com.fl.service.handler.UserService;
import com.fl.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    /*@Autowired
    private UserDetailsRepository userDetailsRepository;*/

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
}
