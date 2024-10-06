package com.thesis.backend.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserAnswerDto {

    private UUID questionId;

    private UUID answerId;
}
