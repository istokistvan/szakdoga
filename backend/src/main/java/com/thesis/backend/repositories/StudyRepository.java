package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyRepository extends JpaRepository<QuizEntity, UUID> {

    public Optional<QuizEntity> findQuizById(@NonNull UUID id);
}
