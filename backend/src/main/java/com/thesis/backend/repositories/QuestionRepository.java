package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, UUID> {

    Optional<QuestionEntity> findById(UUID id);

}
