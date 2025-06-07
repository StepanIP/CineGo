package com.ticket.ticketingSystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;

@Builder
public record ScreeningDto(
    Long id,
    LocalDate date,
    LocalTime time,
    FilmDto film) {
}
