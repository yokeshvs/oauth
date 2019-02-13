package com.fl.service.entity;

import javax.validation.constraints.*;
import java.util.List;

public class UserRequest {
    @Size(min = 4, max = 40)
    private String name;

    @Size(min = 3, max = 15)
    private String userName;

    @Size(max = 40)
    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    private List<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
