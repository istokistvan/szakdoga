package com.thesis.backend.models.dto;

import com.thesis.backend.annotations.ValidDate;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class QuizDto {

    @NonNull
    @NotEmpty(message = "Name is required")
    private String name;

    @NonNull
    private Boolean isVisible;

    private String description;

    @ValidDate
    private String availableFrom;

    @ValidDate
    private String availableTo;

    @NonNull
    private QuestionDto[] questions;
}
