package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.Currency;
import com.ticket.ticketingSystem.model.enums.TicketType;

public record TicketBookingDto(TicketType ticketType,
                               Currency currency,
                               int rowsNumber,
                               int seatInRow) {
}
