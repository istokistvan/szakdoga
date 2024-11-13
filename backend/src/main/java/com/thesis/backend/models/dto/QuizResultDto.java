package com.thesis.backend.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuizResultDto {

    private UUID quizId;

    private UserAnswerDto[] userAnswers;
}
