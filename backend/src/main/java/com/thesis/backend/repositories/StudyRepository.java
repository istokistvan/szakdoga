package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyRepository extends CrudRepository<QuizEntity, UUID> {

    Optional<QuizEntity> findQuizById(@NonNull UUID id);
}