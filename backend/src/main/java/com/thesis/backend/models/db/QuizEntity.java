package com.thesis.backend.models.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private boolean isVisible;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime availableFrom;

    private LocalDateTime availableTo;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    public QuizEntity(String name, boolean isVisible, String description, LocalDateTime availableFrom, LocalDateTime availableTo) {
        this.name = name;
        this.isVisible = isVisible;
        this.description = description;
        this.createdAt = getCreatedAt();
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    private LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}