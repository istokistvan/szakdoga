package com.thesis.backend.services;

import com.thesis.backend.models.QuestionType;
import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.QuizResultEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.DashboardQuizDto;
import com.thesis.backend.repositories.DashboardRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DashboardServiceIT {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    void setUp() {
        dashboardRepository.deleteAll();
    }

    @Test
    void testGetFilledQuizzesCountByUser() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123");
        userRepository.save(user);

        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, null, "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);

        quizRepository.save(quizEntity);

        dashboardRepository.save(new QuizResultEntity(quizEntity, user, 1.0F, new ArrayList<>()));

        // when
        Integer result = dashboardService.getFilledQuizzesCountByUser();

        // then
        assertEquals(1, result);
    }

    @Test
    void testGetQuizzes() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123");
        userRepository.save(user);

        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, null, "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);

        quizRepository.save(quizEntity);

        // when
        List<DashboardQuizDto> result = dashboardService.getQuizzes();

        // then
        assertEquals(1, result.size());
    }

}
