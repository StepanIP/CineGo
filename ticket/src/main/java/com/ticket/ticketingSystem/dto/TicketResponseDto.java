package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.Currency;
import com.ticket.ticketingSystem.model.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TicketResponseDto {

    private Long id;

    private String name;

    private String filmTitle;

    private LocalDate screeningDate;

    private LocalTime screeningTime;

    private BigDecimal ticketPrice;

    private int rowsNumber;

    private int seatInRow;

    private int roomNumber;

    private TicketStatus status;

    private double price;

    private Currency currency;

}
