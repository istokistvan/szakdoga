package com.thesis.backend.controllers;

import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto dto) {
        try {
            quizService.createQuiz(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create quiz!");
        }
    }
}
