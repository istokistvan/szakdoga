package com.thesis.backend.models.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user_id;

    private Float score;

    @OneToMany(mappedBy = "quizResult", cascade = CascadeType.ALL)
    private List<UserAnswerEntity> userAnswerEntities;

    public QuizResultEntity(QuizEntity quiz, UserEntity user, float score, List<UserAnswerEntity> userAnswers) {
        this.quiz_id = quiz;
        this.user_id = user;
        this.score = score;
        this.userAnswerEntities = userAnswers;
    }
}