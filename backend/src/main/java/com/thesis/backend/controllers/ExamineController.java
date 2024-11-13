package com.thesis.backend.controllers;

import com.thesis.backend.models.dto.QuizExamineDto;
import com.thesis.backend.services.ExamineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/examine")
public class ExamineController {

    private final ExamineService examineService;

    public ExamineController(ExamineService examineService) {
        this.examineService = examineService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizExamineDto>> examineAll() {
        try {
            return ResponseEntity.ok(examineService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizExamineDto> examineQuiz(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(examineService.examineQuiz(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/password/{id}")
    public ResponseEntity<String> checkPassword(@RequestBody String password, @PathVariable UUID id) {
        try {
            if (examineService.checkPassword(password, id)) {
                return ResponseEntity.ok("Password is correct!");
            } else {
                return ResponseEntity.badRequest().body("Password is incorrect!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to check password!");
        }
    }
}
