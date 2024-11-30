package com.thesis.backend.models.db;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String fullName;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<QuizEntity> quizzes;
}
