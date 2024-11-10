package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.dto.AnswerDto;
import com.thesis.backend.models.dto.QuestionDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class QuestionDtoConverter {

    public static List<QuestionEntity> convertToEntityList(@NonNull List<QuestionDto> dto, QuizEntity quiz) {
        List<QuestionEntity> result = new ArrayList<>();

        for (QuestionDto questionDto : dto) {
            QuestionEntity question = new QuestionEntity();

            question.setQuiz(quiz);
            question.setQuestion(questionDto.getQuestion());
            question.setQuestionType(questionDto.getQuestionType());
            question.setPoints(questionDto.getPoints());
            question.setCorrectAnswer(questionDto.getCorrectAnswer());
            if (questionDto.getQuestionType().toString().equals("TRUE_FALSE")) {
                question.setAnswers(AnswerDtoConverter.convertToEntityList(
                        List.of(new AnswerDto("True", !questionDto.getIsNegated(), 0), new AnswerDto("False", questionDto.getIsNegated(), 0)), question)
                );
            } else {
                question.setAnswers(AnswerDtoConverter.convertToEntityList(questionDto.getAnswers(), question));
            }

            result.add(question);
        }

        return result;
    }
}
