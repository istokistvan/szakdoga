package com.thesis.backend.services;

import com.thesis.backend.models.db.*;
import com.thesis.backend.models.dto.QuizResultDto;
import com.thesis.backend.models.dto.UserAnswerDto;
import com.thesis.backend.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());

        List<UserAnswerEntity> userAnswers = new ArrayList<>();

        float score = 0;

        for (UserAnswerDto dto : quizResultDto.getUserAnswers()) {

            Optional<QuestionEntity> question = questionRepository.findById(dto.getQuestionId());
            Optional<AnswerEntity> userAnswer;

            if (question.isPresent()) {
                switch (question.get().getQuestionType()) {
                    case NUMBER:
                        userAnswer = Optional.of(question.get().getAnswers().get(0));
                        float answer = Float.parseFloat(question.get().getAnswers().get(0).getAnswer());
                        int errorTolerance = question.get().getAnswers().get(0).getErrorTolerance();
                        if (answer - errorTolerance <= Float.parseFloat(dto.getAnswer()) && Float.parseFloat(dto.getAnswer()) <= answer + errorTolerance) {
                            score += question.get().getPoints();
                        }

                        userAnswers.add(new UserAnswerEntity(
                                question.get(),
                                userAnswer.get()
                        ));

                        break;
                    case MULTIPLE_CHOICE:
                        float oneCorrect = (float) (question.get().getPoints() * 1.0 / question.get().getAnswers().stream().filter(AnswerEntity::isCorrect).count());
                        userAnswer = answerRepository.findById(UUID.fromString(dto.getAnswer()));
                        if (userAnswer.isPresent() && userAnswer.get().isCorrect()) {
                            score += oneCorrect;
                        }
                        userAnswer.ifPresent(answerEntity -> userAnswers.add(new UserAnswerEntity(
                                question.get(),
                                answerEntity
                        )));

                        break;
                    default:
                        userAnswer = answerRepository.findById(UUID.fromString(dto.getAnswer()));
                        if (userAnswer.isPresent() && userAnswer.get().isCorrect()) {
                            score += question.get().getPoints();
                        }
                        userAnswer.ifPresent(answerEntity -> userAnswers.add(new UserAnswerEntity(
                                question.get(),
                                answerEntity
                        )));
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
