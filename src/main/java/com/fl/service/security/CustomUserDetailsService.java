package com.fl.service.security;

import com.fl.service.entity.User;
import com.fl.service.entity.UserPrincipal;
import com.fl.service.entity.UserRoles;
import com.fl.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        List<User> users = userMapper.getUserByUserName(userName);
        return loadUserDetails(users);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        List<User> users = userMapper.getUserById(id);
        return loadUserDetails(users);
    }

    private UserDetails loadUserDetails(List<User> users) {
        if (users != null && users.size() == 1){
            User user = users.get(0);
            List<UserRoles> userRoles = userMapper.getUserRolesById(user.getUserId());
            if (userRoles != null && userRoles.size() == 1)
                return UserPrincipal.create(user, userRoles.get(0));
            return UserPrincipal.create(user, null);
        }
        return null;
    }
}