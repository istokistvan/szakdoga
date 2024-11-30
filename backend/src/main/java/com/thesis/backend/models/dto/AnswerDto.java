package com.thesis.backend.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class AnswerDto {

    @NonNull
    @NotEmpty(message = "Answer is required")
    private String answer;

    @NonNull
    private Boolean isCorrect;

    @NonNull
    private Integer errorTolerance;
}
