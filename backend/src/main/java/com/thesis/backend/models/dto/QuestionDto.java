package com.thesis.backend.models.dto;

import com.thesis.backend.models.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

@Data
@EqualsAndHashCode
public class QuestionDto {

    @NonNull
    @NotEmpty(message = "Question is required")
    private String question;

    @NonNull
    private QuestionType questionType;

    @NonNull
    private Integer points;

    private String correctAnswer;

    @NonNull
    private List<AnswerDto> answers;

    private Boolean isNegated;
}
