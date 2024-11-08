package com.eventmanagement.event_creation_service.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.event_creation_service.entity.AuthRequest;
import com.eventmanagement.event_creation_service.entity.User;
import com.eventmanagement.event_creation_service.util.JwtUtils;


//@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api")
public class LoginController {

    private final JwtUtils jwtUtils;

    public LoginController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        if ("user".equals(authRequest.getUsername()) && "mypass".equals(authRequest.getPassword())) {
            User user = new User();
            user.setName(authRequest.getUsername());
            String token = jwtUtils.generateJwt(user);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
