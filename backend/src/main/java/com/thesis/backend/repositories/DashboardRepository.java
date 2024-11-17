package com.thesis.backend.repositories;

import com.thesis.backend.models.db.QuizResultEntity;
import com.thesis.backend.models.db.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardRepository extends CrudRepository<QuizResultEntity, UUID> {

    @Query("SELECT q FROM QuizResultEntity q WHERE q.user_id = :user")
    List<QuizResultEntity> getFilledQuizzesCountByUser(UserEntity user);
}
