package com.fl.service.mapper;

import com.fl.service.entity.User;
import com.fl.service.entity.UserRoles;
import com.fl.service.entity.UserVerification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> getUserByUserName(String userName);

    List<User> getUserById(Long id);

    void addUser(User user);

    List<User> checkUserNameExists(String userName);

    List<User> checkEmailExists(String email);

    List<User> getAllUsers();

    void addRoles(UserRoles userRoles);

    List<UserRoles> getUserRolesById(Long id);

    void updateUserRoles(UserRoles userRoles);

    void addUserVerification(UserVerification userVerification);

    List<UserVerification> getUserVerification(String verificationId);

    void updateUserStatus(String userName);
}
