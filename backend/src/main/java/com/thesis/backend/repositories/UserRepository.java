package com.thesis.backend.repositories;

import com.thesis.backend.models.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String userName);

    Optional<UserEntity> findByEmail(String email);
}
