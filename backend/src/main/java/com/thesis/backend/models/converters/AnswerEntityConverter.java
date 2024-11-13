package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.AnswerEntity;
import com.thesis.backend.models.dto.AnswerDto;
import com.thesis.backend.models.dto.AnswerExamineDto;
import lombok.NonNull;

import java.util.List;

public class AnswerEntityConverter {

    public static List<AnswerDto> convertToDtoList(List<AnswerEntity> answers) {
        return answers.stream()
                .map(answer -> new AnswerDto(
                        answer.getAnswer(),
                        answer.isCorrect(),
                        answer.getErrorTolerance()
                ))
                .toList();
    }

    public static AnswerExamineDto convertToExamineDto(AnswerEntity answer) {
        return new AnswerExamineDto(
                answer.getId(),
                answer.getAnswer()
        );
    }

    public static List<AnswerExamineDto> convertToExamineDtoList(@NonNull List<AnswerEntity> all) {
        return all.stream().map(AnswerEntityConverter::convertToExamineDto).toList();
    }
}
