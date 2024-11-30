package com.thesis.backend.services;

import com.thesis.backend.models.QuestionType;
import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.QuestionDto;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.repositories.QuizRepository;
import com.thesis.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class QuizServiceIT {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        quizRepository.deleteAll();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testCreateQuiz() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123");
        userRepository.save(user);

        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, passwordEncoder.encode("Abc_123"), "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);

        QuestionDto questionDto = new QuestionDto("Test question", QuestionType.TRUE_FALSE, 1, new ArrayList<>());
        questionDto.setIsNegated(false);

        QuizDto dto = new QuizDto(
                "Test quiz",
                true,
                "Abc_123",
                "Test description",
                now.minusHours(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                now.plusHours(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                List.of(questionDto)
        );


        quizService.createQuiz(dto);

        // when
        Iterable<QuizEntity> result = quizRepository.findAll();

        // then
        assertTrue(result.iterator().hasNext());
        assertEquals(quizEntity.getName(), result.iterator().next().getName());

        assertThrows(IllegalArgumentException.class, () -> quizService.createQuiz(dto));

        dto.setName("");
        assertThrows(IllegalArgumentException.class, () -> quizService.createQuiz(dto));

        dto.setName("Test quiz2");
        dto.setPassword("");
        quizService.createQuiz(dto);
        assertTrue(result.iterator().hasNext());
        assertEquals(quizEntity.getName(), result.iterator().next().getName());
    }
}
