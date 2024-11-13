package com.thesis.backend.models.converters;

import com.thesis.backend.models.db.QuizEntity;
import com.thesis.backend.models.db.UserEntity;
import com.thesis.backend.models.dto.QuizDto;
import com.thesis.backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizDtoConverter {

    public static QuizEntity convertToEntity(QuizDto dto, UserRepository userRepository) {
        QuizEntity result = new QuizEntity(
                dto.getName(),
                dto.getIsVisible(),
                dto.getPassword(),
                dto.getDescription(),
                LocalDateTime.parse(dto.getAvailableFrom(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                LocalDateTime.parse(dto.getAvailableTo(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
        );

        result.setQuestions(QuestionDtoConverter.convertToEntityList(dto.getQuestions(), result));
        result.setAuthor(getAuthor(userRepository));

        return result;
    }

    private static UserEntity getAuthor(UserRepository userRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
