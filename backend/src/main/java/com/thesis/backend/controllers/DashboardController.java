package com.thesis.backend.controllers;

import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/filled")
    public ResponseEntity<Integer> filledQuizzes() {
        try {
            return ResponseEntity.ok(dashboardService.getFilledQuizzesCountByUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizDto>> quizzes() {
        try {
            return ResponseEntity.ok(dashboardService.getQuizzes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
