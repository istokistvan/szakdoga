package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamineRepository extends CrudRepository<QuizEntity, UUID> {

    @NonNull
    @Query("SELECT q FROM QuizEntity q WHERE q.isVisible = true AND q.availableFrom <= :now AND q.availableTo >= :now")
    List<QuizEntity> getAllPublic(@Param("now") LocalDateTime now);

    Optional<QuizEntity> findQuizById(@NonNull UUID id);
}