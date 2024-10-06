package com.thesis.backend.models.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<UserAnswerEntity> userAnswerEntities;

    private String answer;

    private boolean isCorrect;

    private int errorTolerance;

    public AnswerEntity(String answer, boolean isCorrect, int errorTolerance) {
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.errorTolerance = errorTolerance;
    }
}
