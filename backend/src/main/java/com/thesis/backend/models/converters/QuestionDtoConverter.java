package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.dto.QuestionDto;

import java.util.ArrayList;
import java.util.List;

public class QuestionDtoConverter {

    public static List<QuestionEntity> convertToEntityList(QuestionDto[] dto, QuizEntity quiz) {
        List<QuestionEntity> result = new ArrayList<>();

        for (QuestionDto questionDto : dto) {
            QuestionEntity question = new QuestionEntity();

            question.setQuiz(quiz);
            question.setQuestion(questionDto.getQuestion());
            question.setQuestionType(questionDto.getQuestionType());
            question.setPoints(questionDto.getPoints());
            question.setCorrectAnswer(questionDto.getCorrectAnswer());
            question.setAnswers(AnswerDtoConverter.convertToEntityList(questionDto.getAnswers(), question));

            result.add(question);
        }

        return result;
    }
}
