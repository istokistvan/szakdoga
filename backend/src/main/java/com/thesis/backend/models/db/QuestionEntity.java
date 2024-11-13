package com.thesis.backend.models.db;

import com.thesis.backend.models.QuestionType;
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
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerEntity> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<UserAnswerEntity> userAnswerEntities;

    private int points;

    private String correctAnswers;

    public QuestionEntity(QuizEntity quiz, String question, QuestionType questionType, List<AnswerEntity> answers, int points, String correctAnswers) {
        this.quiz = quiz;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
        this.points = points;
        this.correctAnswers = correctAnswers;
    }
}
