package com.thesis.backend.models.dto;

import com.thesis.backend.models.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionExamineDto {

    private UUID id;

    private String question;

    private QuestionType questionType;

    private Integer points;

    private List<AnswerExamineDto> answers;
}