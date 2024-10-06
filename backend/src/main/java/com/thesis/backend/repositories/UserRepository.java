package com.thesis.backend.repositories;

import com.thesis.backend.models.db.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String userName);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);
}
