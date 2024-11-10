package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.AnswerEntity;
import com.thesis.backend.models.db.QuestionEntity;
import com.thesis.backend.models.dto.AnswerDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AnswerDtoConverter {

    public static List<AnswerEntity> convertToEntityList(@NonNull List<AnswerDto> dto, QuestionEntity question) {
        List<AnswerEntity> result = new ArrayList<>();

        for (AnswerDto answerDto : dto) {
            AnswerEntity answer = new AnswerEntity();

            answer.setQuestion(question);
            answer.setAnswer(answerDto.getAnswer());
            answer.setCorrect(answerDto.getIsCorrect());
            answer.setErrorTolerance(answerDto.getErrorTolerance());

            result.add(answer);
        }

        return result;
    }
}
