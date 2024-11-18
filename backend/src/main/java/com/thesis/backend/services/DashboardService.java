package com.thesis.backend.services;

import com.thesis.backend.models.converters.QuizEntityConverter;
import com.thesis.backend.models.db.QuizResultEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.DashboardQuizDto;
import com.thesis.backend.repositories.DashboardRepository;
import com.thesis.backend.repositories.QuizRepository;
import com.thesis.backend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public DashboardService(DashboardRepository dashboardRepository, UserRepository userRepository, QuizRepository quizRepository) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public Integer getFilledQuizzesCountByUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        List<QuizResultEntity> res = dashboardRepository.getFilledQuizzesCountByUser(user.get());

        return res.size();
    }

    public List<DashboardQuizDto> getQuizzes() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return QuizEntityConverter.convertToDashboardDtoList(quizRepository.findAllByAuthor(user.get()));
    }
}
