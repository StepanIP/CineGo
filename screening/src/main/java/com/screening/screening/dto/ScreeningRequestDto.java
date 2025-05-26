package com.screening.screening.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.screening.cinema.Cinema;

public record ScreeningRequestDto(LocalDate date, LocalTime time, Long cinemaId) {
}
