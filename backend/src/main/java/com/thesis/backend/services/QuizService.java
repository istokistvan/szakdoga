package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizDtoConverter;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.repositories.QuizRepository;
import com.thesis.backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void createQuiz(QuizDto dto) {
        if (dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        if (quizRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Quiz with this name already exists!");
        }

        if (!dto.getPassword().isEmpty()) {
            String password = dto.getPassword();
            dto.setPassword(passwordEncoder.encode(password));
        }

        quizRepository.save(QuizDtoConverter.convertToEntity(dto, userRepository));
    }
}
