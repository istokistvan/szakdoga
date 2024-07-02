package com.thesis.backend.controllers;

import com.thesis.backend.model.db.User;
import com.thesis.backend.model.dto.UserLoginDto;
import com.thesis.backend.model.dto.UserRegistrationDto;
import com.thesis.backend.services.UserService;
import com.thesis.backend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/register", consumes = "multipart/form-data")
    public @ResponseBody ResponseEntity<?> registerUser(@Validated @ModelAttribute UserRegistrationDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register user!");
        }
    }

    @PostMapping(value = "/login", consumes = "multipart/form-data")
    public @ResponseBody ResponseEntity<?> loginUser(@Validated @ModelAttribute UserLoginDto userDto) {
        try {
            User user = userService.login(userDto);
            String token = jwtUtil.generateToken(user.getUserName());

            return ResponseEntity.ok().headers(headers -> headers.add("Set-Cookie", "Authorization=" + token + "; Max-Age=" + 60 * 60 * 24)).body("User logged in successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to login user!");
        }
    }
}
