package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizEntityConverter;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.repositories.StudyRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public QuizDto studyQuiz(UUID id) {
        return QuizEntityConverter.convertToDto(studyRepository.findQuizById(id));
    }
}
