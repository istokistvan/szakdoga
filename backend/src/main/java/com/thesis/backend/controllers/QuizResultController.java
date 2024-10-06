package com.thesis.backend.controllers;

import com.thesis.backend.models.dto.QuizResultDto;
import com.thesis.backend.services.QuizResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/result")
public class QuizResultController {

    private final QuizResultService quizResultService;

    public QuizResultController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitResult(@RequestBody QuizResultDto quizResultDto) {
        try {
            float score = quizResultService.submitQuizResult(quizResultDto);

            return ResponseEntity.ok(score + "");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to submit quiz result!");
        }
    }
}
