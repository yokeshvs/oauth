package com.fl.service.handler;

import com.fl.service.entity.ServiceAPIResponse;
import com.fl.service.entity.UserRequest;
import com.fl.service.entity.User;

import java.util.List;

public interface UserService {

    ServiceAPIResponse registerUser(UserRequest userRequest);

    User getUserDetails(String userName);

    List<User> getAllUserDetails();

    ServiceAPIResponse updateUserRoles (String userName, UserRequest userRequest);
}
