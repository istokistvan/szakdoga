package com.thesis.backend.services;

import com.thesis.backend.models.db.*;
import com.thesis.backend.models.dto.QuizResultDto;
import com.thesis.backend.models.dto.UserAnswerDto;
import com.thesis.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuizResultService(QuizRepository quizRepository, UserRepository userRepository, QuizResultRepository quizResultRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizResultRepository = quizResultRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public float submitQuizResult(QuizResultDto quizResultDto) {

        Optional<QuizEntity> quiz = quizRepository.findById(quizResultDto.getQuizId());
        Optional<UserEntity> user = userRepository.findById(quizResultDto.getExaminer());

        List<UserAnswerEntity> userAnswers = new ArrayList<>();

        float score = 0;

        for (UserAnswerDto dto : quizResultDto.getUserAnswers()) {

            Optional<QuestionEntity> question = questionRepository.findById(dto.getQuestionId());
            Optional<AnswerEntity> answer = answerRepository.findById(dto.getAnswerId());

            if (question.isPresent() && answer.isPresent()) {
                userAnswers.add(
                        new UserAnswerEntity(
                                question.get(),
                                answer.get()
                        )
                );

                if (answer.get().isCorrect()) {
                    score += question.get().getPoints();
                }
            }
        }

        if (quiz.isPresent() && user.isPresent()) {
            quizResultRepository.save(new QuizResultEntity(
                    quiz.get(),
                    user.get(),
                    score,
                    userAnswers
            ));
        }

        return score;
    }
}
