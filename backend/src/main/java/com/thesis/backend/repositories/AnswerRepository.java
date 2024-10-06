package com.thesis.backend.repositories;

import com.thesis.backend.models.db.AnswerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository extends CrudRepository<AnswerEntity, UUID> {

    Optional<AnswerEntity> findById(UUID id);
}
