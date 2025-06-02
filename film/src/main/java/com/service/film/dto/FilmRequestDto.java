package com.service.film.dto;

import java.time.LocalDate;
import java.util.List;

public record FilmRequestDto(
    String title,
    String description,
    List<Long> genreIds,
    int durationFilmInMinutes,
    LocalDate releaseDate,
    String director,
    String country,
    Integer ageRestriction,
    String trailerUrl,
    String coverImage,
    String titleImage,
    List<Long> actorIds
) {}
