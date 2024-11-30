package com.thesis.backend.models.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AnswerExamineDto {

    private UUID id;

    private String answer;
}
