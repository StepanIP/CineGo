package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;

import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;

public interface DiscountStrategy {

    BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening);
}
