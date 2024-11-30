package com.thesis.backend.services;

import com.thesis.backend.models.QuestionType;
import com.thesis.backend.models.converters.QuestionEntityConverter;
import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.StudyDto;
import com.thesis.backend.repositories.StudyRepository;
import com.thesis.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudyServiceIT {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyService studyService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        studyRepository.deleteAll();
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
        studyRepository.save(quizEntity);

        StudyDto studyDto = new StudyDto(quizEntity.getId(), "Test quiz", "Test description", QuestionEntityConverter.convertToDtoList(questions));

        // when
        List<StudyDto> all = studyService.getAll();

        // then
        assertEquals(1, all.size());
        assertEquals(studyDto, all.get(0));
    }

    @Test
    void testStudyQuiz() {

        // given
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = userRepository.save(new UserEntity("Teszt Elek", "admin", "teszt@teszt.com", "Admin_123"));

        QuizEntity quizEntity = new QuizEntity("Test quiz", true, null, "Test description", now.minusHours(3), now.plusHours(3));


        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(new QuestionEntity(quizEntity, "Test question", QuestionType.TRUE_FALSE, new ArrayList<>(), 1, ""));

        quizEntity.setAuthor(user);
        quizEntity.setQuestions(questions);
        studyRepository.save(quizEntity);

        UUID id = quizEntity.getId();
        StudyDto studyDto = new StudyDto(id, "Test quiz", "Test description", QuestionEntityConverter.convertToDtoList(questions));

        // when
        StudyDto studyQuiz = studyService.studyQuiz(id);

        // then
        assertEquals(studyDto, studyQuiz);
    }
}
