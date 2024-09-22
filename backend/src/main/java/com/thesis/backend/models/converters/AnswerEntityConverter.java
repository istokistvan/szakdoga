package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.AnswerEntity;
import com.thesis.backend.models.dto.AnswerDto;

import java.util.List;

public class AnswerEntityConverter {

    public static AnswerDto[] convertToDtoList(List<AnswerEntity> answers) {
        return answers.stream()
                .map(answer -> new AnswerDto(
                        answer.getAnswer(),
                        answer.isCorrect(),
                        answer.getErrorTolerance()
                ))
                .toArray(AnswerDto[]::new);
    }
}
