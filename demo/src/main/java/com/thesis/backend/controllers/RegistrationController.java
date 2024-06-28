package com.thesis.backend.controllers;

import com.thesis.backend.model.dto.UserRegistrationDto;
import com.thesis.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "multipart/form-data")
    public @ResponseBody ResponseEntity<?> registerUser(@Validated @ModelAttribute UserRegistrationDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register user!");
        }
    }
}
