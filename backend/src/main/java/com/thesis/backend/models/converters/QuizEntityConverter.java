package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.dto.QuizDto;

import java.util.Optional;

public class QuizEntityConverter {

    public static QuizDto convertToDto(Optional<QuizEntity> quiz) {
        return quiz.map(quizEntity -> new QuizDto(
                        quizEntity.getName(),
                        quizEntity.isVisible(),
                        quizEntity.getDescription(),
                        quizEntity.getAvailableFrom().toString(),
                        quizEntity.getAvailableTo().toString(),
                        QuestionEntityConverter.convertToDtoList(quizEntity.getQuestions())
                ))
                .orElse(null);
    }
}
