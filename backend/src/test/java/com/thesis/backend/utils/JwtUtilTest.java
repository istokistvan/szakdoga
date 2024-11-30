package com.thesis.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtUtilTest {

    @Value("${jwt.secret}")
    private String key;

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(key);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("test");
        assertNotNull(token);
    }

    @Test
    void testExtractClaims() {
        String testUserName = "testUser";
        String token = jwtUtil.generateToken(testUserName);

        Claims claims = jwtUtil.extractClaims(token);

        assertNotNull(claims);
        assertEquals(testUserName, claims.getSubject());
    }

    @Test
    void testValidateToken() {
        String testUserName = "testUser";
        String validToken = jwtUtil.generateToken(testUserName);

        String differentUserName = "differentUser";
        String invalidToken = jwtUtil.generateToken(differentUserName);

        assertTrue(jwtUtil.validateToken(validToken, testUserName));

        assertFalse(jwtUtil.validateToken(invalidToken, testUserName));
    }

    @Test
    void testIsTokenExpired() throws InterruptedException {
        String testUserName = "testUser";
        String token = jwtUtil.generateToken(testUserName);

        String expiredToken = Jwts.builder()
                .subject(testUserName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();

        assertTrue(jwtUtil.validateToken(token, testUserName));

        Thread.sleep(1000);
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(expiredToken, testUserName));
    }
}
