package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizResultEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResultEntity, UUID> {
}
