package com.thesis.backend.services;

import com.thesis.backend.exceptions.ExistingEmailException;
import com.thesis.backend.exceptions.ExistingUsernameException;
import com.thesis.backend.exceptions.InvalidPasswordException;
import com.thesis.backend.models.converters.UserDtoConverter;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserLoginDto;
import com.thesis.backend.models.dto.UserRegistrationDto;
import com.thesis.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void register(UserRegistrationDto dto) {
        userRepository.findByUsername(dto.getUserName()).ifPresent(user -> {
            throw new ExistingUsernameException("Username already exists!");
        });

        userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new ExistingEmailException("Email already exists!");
        });

        userRepository.save(UserDtoConverter.convertToEntity(dto, passwordEncoder));
    }

    public UserEntity login(UserLoginDto dto) {
        UserEntity userEntity = userRepository.findByUsername(dto.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        }

        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return User.withUsername(userEntity.getUsername()).password(userEntity.getPassword()).build();
    }
}
