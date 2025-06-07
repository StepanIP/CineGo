package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.TicketStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record TicketBookedDto(
    String filmTitle,
    LocalDate screeningDate,
    LocalTime screeningTime,
    int rowsNumber,
    int seatInRow,
    TicketStatus status) {
}
