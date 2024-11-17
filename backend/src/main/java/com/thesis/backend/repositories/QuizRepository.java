package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, UUID> {

    Optional<QuizEntity> findByName(@NonNull String name);

    @NonNull
    Optional<QuizEntity> findById(@NonNull UUID uuid);

    @Query("SELECT q FROM QuizEntity q WHERE q.author = :author")
    List<QuizEntity> findAllByAuthor(@NonNull UserEntity author);
}
