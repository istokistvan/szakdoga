package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizDtoConverter;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.repositories.QuizRepository;
import com.thesis.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public void createQuiz(@Validated QuizDto dto) {

        if (dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        if (quizRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Quiz with this name already exists!");
        }

        quizRepository.save(QuizDtoConverter.convertToEntity(dto, userRepository));
    }
}
