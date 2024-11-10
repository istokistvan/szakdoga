package com.thesis.backend.services;

import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserLoginDto;
import com.thesis.backend.models.dto.UserRegistrationDto;
import com.thesis.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setFullName("John Doe");
        dto.setUserName("john");
        dto.setEmail("john@example.com");
        dto.setPassword("Abc_123");

        when(userRepository.findByUsername(dto.getUserName())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        userService.register(dto);
    }

    @Test
    public void testLoginSuccess() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUserName("john");
        dto.setPassword("Abc_123");

        UserEntity userEntity = new UserEntity("John Doe", "john", "john@example.com", "Abc_123");

        when(userRepository.findByUsername(dto.getUserName())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())).thenReturn(true);

        UserEntity user = userService.login(dto);

        assertNotNull(user);
        assertEquals(userEntity, user);
    }

}
