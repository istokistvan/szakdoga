package com.thesis.backend.models.db;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private boolean visible;

    private String password;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime availableFrom;

    private LocalDateTime availableTo;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    public QuizEntity(String name, boolean visible, String password, String description, LocalDateTime availableFrom, LocalDateTime availableTo) {
        this.name = name;
        this.visible = visible;
        this.password = password;
        this.description = description;
        this.createdAt = getCreatedAt();
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    private LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}