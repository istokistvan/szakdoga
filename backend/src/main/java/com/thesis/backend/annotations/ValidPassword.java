package com.thesis.backend.annotations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "Password is required")
@Size(min = 8, message = "Password should have at least 8 characters")
@Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*_+\\-\\\\()\\[\\]/'\",.:?=])[a-zA-Z0-9!@#$%^&*_+\\-\\\\()\\[\\]/'\",.:?=]{8,16}$",
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
public @interface ValidPassword {
}
