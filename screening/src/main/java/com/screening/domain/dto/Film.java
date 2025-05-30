package com.screening.domain.dto;

import java.time.LocalDate;
import java.util.List;

public record Film(
    Long id,
    String title,
    String description,
    List<GenreResponseDto> genres,
    int durationFilmInMinutes,
    LocalDate releaseDate,
    String director,
    Double rating,
    String country,
    Integer ageRestriction,
    List<ActorResponseDto> actors
) {}



