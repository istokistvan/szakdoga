package com.thesis.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.UserLoginDto;
import com.thesis.backend.models.dto.UserRegistrationDto;
import com.thesis.backend.services.UserService;
import com.thesis.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${jwt.secret}")
    private String key;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterSuccess() throws Exception {
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setUserName("testUserName");
        userDto.setPassword("Abc_123");
        userDto.setEmail("test@test.com");
        userDto.setFullName("Test User");

        mockMvc.perform(multipart("/api/auth/register")
                        .param("userDto", userDto.toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        UserLoginDto userDto = new UserLoginDto();
        userDto.setUserName("testUserName");
        userDto.setPassword("Abc_123");

        UserEntity userEntity = new UserEntity("Test Fullname", "testUserName", "test@test.com", "Abc_123");

        when(userService.login(userDto)).thenReturn(userEntity);
        when(jwtUtil.generateToken("testUserName")).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(new ObjectMapper().writeValueAsString(userDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("testToken"));
    }
}
