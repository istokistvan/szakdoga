package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<QuizEntity, UUID> {

    public Optional<QuizEntity> findByName(@NonNull String name);

}
