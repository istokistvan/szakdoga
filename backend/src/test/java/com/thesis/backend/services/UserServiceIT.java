package com.thesis.backend.services;

import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserLoginDto;
import com.thesis.backend.models.dto.UserRegistrationDto;
import com.thesis.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testRegister() {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFullName("Teszt Elek");
        userRegistrationDto.setUserName("admin");
        userRegistrationDto.setEmail("teszt@teszt.com");
        userRegistrationDto.setPassword("Admin_123");

        userService.register(userRegistrationDto);

        // when
        Optional<UserEntity> user = userRepository.findByEmail(userRegistrationDto.getEmail());

        // then
        assertTrue(user.isPresent());
        assertEquals(userRegistrationDto.getEmail(), user.get().getEmail());

        assertThrows(Exception.class, () -> userService.register(userRegistrationDto));

        userRegistrationDto.setUserName("admin2");
        assertThrows(Exception.class, () -> userService.register(userRegistrationDto));
    }

    @Test
    void testLogin() {
        // given
        UserEntity userEntity = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", passwordEncoder.encode("Admin_123"));
        userRepository.save(userEntity);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserName("admin");
        userLoginDto.setPassword("Admin_123");

        // when
        UserEntity user = userService.login(userLoginDto);

        // then
        assertEquals(userEntity, user);

        userLoginDto.setPassword("Admin_1234");
        assertThrows(Exception.class, () -> userService.login(userLoginDto));
    }

    @Test
    void testLoadUserByUsername() {
        // given
        UserEntity userEntity = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", passwordEncoder.encode("Admin_123"));
        userRepository.save(userEntity);

        // when
        UserDetails user = userService.loadUserByUsername("admin");

        // then
        assertEquals(userEntity.getUsername(), user.getUsername());
    }
}
