package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizEntityConverter;
import com.thesis.backend.models.dto.StudyDto;
import com.thesis.backend.repositories.StudyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public List<StudyDto> getAll() {
        return QuizEntityConverter.convertToStudyDtoList(studyRepository.findAllAvailable(LocalDateTime.now()));
    }

    public StudyDto studyQuiz(UUID id) {
        return QuizEntityConverter.convertToStudyDto(studyRepository.findQuizById(id, LocalDateTime.now()).orElseThrow());
    }
}
