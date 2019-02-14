package com.fl.service.controller;

import com.fl.service.entity.LoginRequest;
import com.fl.service.entity.UserRequest;
import com.fl.service.handler.AuthService;
import com.fl.service.handler.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/oauth")
public class AppController {

    private final UserService userService;

    private final AuthService authService;

    @Autowired
    public AppController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody UserRequest userRequest) {
        // Creating user's account
        return ResponseEntity.ok().body(userService.registerUser(userRequest));
        /*URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{userName}")
                .buildAndExpand(user.getUserName()).toUri();
        return ResponseEntity.created(location).body(new ServiceAPIResponse(true, "User registered successfully"));*/
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmEmail")
    public ResponseEntity confirmEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(userService.confirmEmail(token));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verificationEmail")
    public ResponseEntity sendEmailVerification(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.sendEmailVerification(userRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity getAllUserDetails() {
        return ResponseEntity.ok().body(userService.getAllUserDetails());
    }

    @PreAuthorize("hasAuthority('BASIC')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userName}")
    public ResponseEntity getUserDetails(@PathVariable("userName") String userName) {
        /*List<User> users = userDetailsRepository.findByEmail(email);
        return users.get(0);*/
        return ResponseEntity.ok().body(userService.getUserDetails(userName));
    }

    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(method = RequestMethod.PUT, value = "/users/{userName}")
    public ResponseEntity updateUserRoles(@PathVariable("userName") String userName, @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(userService.updateUserRoles(userName, userRequest));
    }

}
