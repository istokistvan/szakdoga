package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.dto.QuestionDto;
import com.thesis.backend.models.dto.QuestionExamineDto;
import lombok.NonNull;

import java.util.List;

public class QuestionEntityConverter {

    public static QuestionDto convertToDto(QuestionEntity question) {
        return new QuestionDto(
                question.getQuestion(),
                question.getQuestionType(),
                question.getPoints(),
                AnswerEntityConverter.convertToDtoList(question.getAnswers())
        );
    }

    public static List<QuestionDto> convertToDtoList(List<QuestionEntity> questions) {
        return questions.stream().map(QuestionEntityConverter::convertToDto).toList();
    }

    public static QuestionExamineDto convertToExamineDto(QuestionEntity question) {
        return new QuestionExamineDto(
                question.getId(),
                question.getQuestion(),
                question.getQuestionType(),
                question.getPoints(),
                AnswerEntityConverter.convertToExamineDtoList(question.getAnswers())
        );
    }

    public static List<QuestionExamineDto> convertToExamineDtoList(@NonNull List<QuestionEntity> all) {
        return all.stream().map(QuestionEntityConverter::convertToExamineDto).toList();
    }
}
