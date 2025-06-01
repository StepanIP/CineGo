package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.Currency;
import com.ticket.ticketingSystem.model.enums.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record TicketDto(String name,
                        String filmTitle,
                        LocalDate screeningDate,
                        LocalTime screeningTime,
                        BigDecimal ticketPrice,
                        int rowsNumber,
                        int seatInRow,
                        int roomNumber,
                        TicketType ticketType,
                        Currency currency) {
}
