package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamineRepository extends CrudRepository<QuizEntity, UUID> {

    @NonNull
    @Query("SELECT q FROM QuizEntity q WHERE q.isVisible = true AND q.availableFrom <= :now AND q.availableTo >= :now")
    List<QuizEntity> getAllPublic(@Param("now") LocalDateTime now);

    @Query("SELECT q FROM QuizEntity q WHERE q.id = :id AND q.availableFrom <= :now AND q.availableTo >= :now")
    Optional<QuizEntity> findQuizById(@Param("id") UUID id, @Param("now") LocalDateTime now);
}
