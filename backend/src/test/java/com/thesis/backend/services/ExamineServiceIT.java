package com.thesis.backend.services;

import com.thesis.backend.models.QuestionType;
import com.thesis.backend.models.converters.QuestionEntityConverter;
import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.QuizExamineDto;
import com.thesis.backend.repositories.ExamineRepository;
import com.thesis.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ExamineServiceIT {

    @Autowired
    private ExamineService examineService;

    @Autowired
    private ExamineRepository examineRepository;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        examineRepository.deleteAll();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testGetAll() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = userRepository.save(new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123"));

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, null, "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);
        examineRepository.save(quizEntity);

        QuizExamineDto quizExamineDto = new QuizExamineDto(quizEntity.getId(), "Test quiz", null, "Test description", QuestionEntityConverter.convertToExamineDtoList(questions));

        // when
        List<QuizExamineDto> all = examineService.getAll();

        // then
        assertEquals(1, all.size());
        assertEquals(quizExamineDto, all.get(0));
    }

    @Test
    void testExamineQuiz() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = userRepository.save(new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123"));

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, null, "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);
        examineRepository.save(quizEntity);

        UUID id = quizEntity.getId();
        QuizExamineDto quizExamineDto = new QuizExamineDto(quizEntity.getId(), "Test quiz", null, "Test description", QuestionEntityConverter.convertToExamineDtoList(questions));

        // when
        QuizExamineDto result = examineService.examineQuiz(id);

        // then
        assertEquals(quizExamineDto, result);
    }

    @Test
    void testCheckPassword() {
        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = userRepository.save(new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123"));

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, passwordEncoder.encode("Abc_123"), "Test description", now.minusHours(3), now.plusHours(3));

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);
        examineRepository.save(quizEntity);

        UUID id = quizEntity.getId();

        // when
        boolean result = examineService.checkPassword("Abc_123", id);

        // then
        assertTrue(result);
    }
}
