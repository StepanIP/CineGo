package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;

import com.ticket.ticketingSystem.dto.ScreeningDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;

public interface DiscountStrategy {

    BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening);
}
