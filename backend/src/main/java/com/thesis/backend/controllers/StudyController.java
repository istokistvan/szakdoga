package com.thesis.backend.controllers;

import com.thesis.backend.models.dto.StudyDto;
import com.thesis.backend.services.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudyDto>> studyAll() {
        try {
            return ResponseEntity.ok(studyService.studyAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyDto> studyQuiz(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(studyService.studyQuiz(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
