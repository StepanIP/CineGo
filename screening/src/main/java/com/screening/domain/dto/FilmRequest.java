package com.screening.domain.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record FilmRequest(
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
    String trailerUrl,
    String coverImage,
    String titleImage,
    List<ActorResponseDto> actors
) {}
