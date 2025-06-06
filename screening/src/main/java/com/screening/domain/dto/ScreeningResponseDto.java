package com.screening.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningResponseDto(Long id, LocalDate date, LocalTime time, FilmRequest film, CinemaDto cinema) {
}
