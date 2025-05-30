package com.service.film.dto;

import java.time.LocalDate;
import java.util.List;

public record FilmResponseDto(
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
