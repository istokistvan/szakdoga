package com.thesis.backend.services;

import com.thesis.backend.models.QuestionType;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.QuestionDto;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.models.dto.QuizResultDto;
import com.thesis.backend.models.dto.UserAnswerDto;
import com.thesis.backend.repositories.QuestionRepository;
import com.thesis.backend.repositories.QuizRepository;
import com.thesis.backend.repositories.QuizResultRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class QuizResultServiceIT {

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        quizResultRepository.deleteAll();
    }

    @Test
    void testSubmitQuizResult() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123");
        userRepository.save(user);

        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuestionDto questionDto = new QuestionDto("Test question", QuestionType.TRUE_FALSE, 1, new ArrayList<>());
        questionDto.setIsNegated(false);

        QuizDto dto = new QuizDto(
                "Test quiz",
                true,
                "",
                "Test description",
                now.minusHours(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                now.plusHours(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                List.of(questionDto)
        );

        quizService.createQuiz(dto);

        QuizEntity quiz = quizRepository.findAll().iterator().next();

        List<UserAnswerDto> userAnswers = new ArrayList<>();
        UserAnswerDto userAnswerDto = new UserAnswerDto();
        userAnswerDto.setQuestionId(quiz.getQuestions().get(0).getId());
        userAnswerDto.setAnswer(quiz.getQuestions().get(0).getAnswers().get(0).getId().toString());

        userAnswers.add(userAnswerDto);

        QuizResultDto quizResultDto = new QuizResultDto();
        quizResultDto.setQuizId(quiz.getId());
        quizResultDto.setUserAnswers(userAnswers.toArray(new UserAnswerDto[0]));

        quizResultService.submitQuizResult(quizResultDto);

        // when
        float result = quizResultRepository.findAll().iterator().next().getScore();

        // then
        assertEquals(1.0F, result);

    }
}
