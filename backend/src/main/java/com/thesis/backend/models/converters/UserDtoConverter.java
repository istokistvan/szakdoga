package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDtoConverter {

    public static UserEntity convertToEntity(UserRegistrationDto dto, PasswordEncoder passwordEncoder) {
        return new UserEntity(
                dto.getFullName(),
                dto.getUserName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword())
        );
    }
}
