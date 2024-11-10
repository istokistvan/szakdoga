package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.dto.QuestionDto;

import java.util.List;

public class QuestionEntityConverter {

    public static QuestionDto[] convertToDtoList(List<QuestionEntity> questions) {
        return questions.stream()
                .map(question -> new QuestionDto(
                        question.getQuestion(),
                        question.getQuestionType(),
                        question.getPoints(),
                        List.of(AnswerEntityConverter.convertToDtoList(question.getAnswers()))
                ))
                .toArray(QuestionDto[]::new);
    }
}
