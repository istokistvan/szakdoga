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
@NotBlank(message = "Full name is required")
@Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
@Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]+(?: [\\p{Lu}][\\p{Ll}]+)*(?: [\\p{Lu}][\\p{Ll}]+)?$", message = "Invalid name format")
public @interface ValidFullname {
}
