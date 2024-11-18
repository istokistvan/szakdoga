package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizEntityConverter;
import com.thesis.backend.models.dto.QuizExamineDto;
import com.thesis.backend.repositories.ExamineRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExamineService {

    private final ExamineRepository examineRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ExamineService(ExamineRepository examineRepository) {
        this.examineRepository = examineRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<QuizExamineDto> getAll() {
        return QuizEntityConverter.convertToExamineDtoList(examineRepository.getAllPublic(LocalDateTime.now()));
    }

    public QuizExamineDto examineQuiz(UUID id) {
        return QuizEntityConverter.convertToExamineDto(examineRepository.findQuizById(id, LocalDateTime.now()).orElseThrow());
    }

    public boolean checkPassword(String password, UUID id) {
        String encodedPassword = examineRepository.findQuizById(id, LocalDateTime.now()).orElseThrow().getPassword();
        return passwordEncoder.matches(password, encodedPassword);
    }
}
