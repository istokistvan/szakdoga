package com.thesis.backend.repositories;

import com.thesis.backend.models.db.AnswerEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends CrudRepository<AnswerEntity, UUID> {

    @NonNull
    Optional<AnswerEntity> findById(@NonNull UUID id);
}
