package com.thesis.backend.controllers;

import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserLoginDto;
import com.thesis.backend.models.dto.UserRegistrationDto;
import com.thesis.backend.services.UserService;
import com.thesis.backend.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Validated UserRegistrationDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register user!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Validated UserLoginDto userDto) {
        try {
            UserEntity userEntity = userService.login(userDto);
            String token = jwtUtil.generateToken(userEntity.getUsername());

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to login user!");
        }
    }
}
