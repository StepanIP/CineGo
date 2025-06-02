package com.service.film.service;

import com.service.film.domain.Film;
import com.service.film.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmRatingUpdateJob {

    private final FilmRepository filmRepository;

    @Scheduled(cron = "0 0 0 * * *") // Run at midnight every day
    @Transactional
    public void updateFilmRatings() {
        log.info("Starting daily film ratings update");
        List<Film> films = filmRepository.findAll();
        
        for (Film film : films) {
            double averageRating = film.getComments().stream()
                .mapToInt(comment -> comment.getRating())
                .average()
                .orElse(0.0);
            
            film.setRating(averageRating);
            filmRepository.save(film);
        }
        
        log.info("Completed daily film ratings update");
    }
} 