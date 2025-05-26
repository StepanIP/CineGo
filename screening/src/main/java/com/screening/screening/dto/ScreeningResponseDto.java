package com.screening.screening.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.screening.cinema.CinemaDto;

public record ScreeningResponseDto(Long id, LocalDate date, LocalTime time, Film film, CinemaDto cinema) {
}
