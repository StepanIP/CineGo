package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.Currency;
import lombok.Builder;

@Builder
public record TicketBookingDto(double price,
                               Currency currency,
                               int rowsNumber,
                               int seatInRow) {
}
