package com.thesis.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    @Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]+(?: [\\p{Lu}][\\p{Ll}]+)*(?: [\\p{Lu}][\\p{Ll}]+)?$", message = "Invalid name format")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*_+\\-\\\\()\\[\\]/'\",.:?=])[a-zA-Z0-9!@#$%^&*_+\\-\\\\()\\[\\]/'\",.:?=]{8,16}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;
}
