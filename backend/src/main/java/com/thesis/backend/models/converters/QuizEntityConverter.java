package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.models.dto.QuizExamineDto;
import com.thesis.backend.models.dto.StudyDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class QuizEntityConverter {

    public static QuizDto convertToDto(Optional<QuizEntity> quiz) {
        return quiz.map(quizEntity -> new QuizDto(
                        quizEntity.getName(),
                        quizEntity.isVisible(),
                        quizEntity.getPassword(),
                        quizEntity.getDescription(),
                        quizEntity.getAvailableFrom().toString(),
                        quizEntity.getAvailableTo().toString(),
                        QuestionEntityConverter.convertToDtoList(quizEntity.getQuestions())
                ))
                .orElse(null);
    }

    public static List<QuizDto> convertToDtoList(@NonNull List<QuizEntity> all) {
        return all.stream().map(e -> QuizEntityConverter.convertToDto(Optional.of(e))).toList();
    }

    public static StudyDto convertToStudyDto(QuizEntity quiz) {
        return new StudyDto(
                quiz.getId(),
                quiz.getName(),
                quiz.getDescription(),
                QuestionEntityConverter.convertToDtoList(quiz.getQuestions())
        );
    }

    public static QuizExamineDto convertToExamineDto(QuizEntity quiz) {
        return new QuizExamineDto(
                quiz.getId(),
                quiz.getName(),
                quiz.getPassword(),
                quiz.getDescription(),
                QuestionEntityConverter.convertToExamineDtoList(quiz.getQuestions())
        );
    }

    public static List<StudyDto> convertToStudyDtoList(@NonNull List<QuizEntity> all) {
        return all.stream().map(QuizEntityConverter::convertToStudyDto).toList();
    }

    public static List<QuizExamineDto> convertToExamineDtoList(@NonNull List<QuizEntity> all) {
        return all.stream().map(QuizEntityConverter::convertToExamineDto).toList();
    }
}
