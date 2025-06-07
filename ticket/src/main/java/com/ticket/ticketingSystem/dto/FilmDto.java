package com.ticket.ticketingSystem.dto;

import lombok.Builder;

@Builder
public record FilmDto(
    Long id,
    String title,
    int durationFilmInMinutes) {
}
