package com.screening.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.screening.domain.model.TicketStatus;
import lombok.Builder;
import lombok.Data;

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

    private String currency;

}
