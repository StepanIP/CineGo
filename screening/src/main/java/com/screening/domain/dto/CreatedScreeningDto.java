package com.screening.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreatedScreeningDto(Long id, LocalDate date, LocalTime time, Long filmId, Long cinemaId) {
}
