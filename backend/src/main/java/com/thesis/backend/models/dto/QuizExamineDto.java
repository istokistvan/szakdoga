package com.thesis.backend.models.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuizExamineDto {
    private UUID id;

    private String name;

    private String password;

    private String description;

    private List<QuestionExamineDto> questions;
}
