package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;

import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;

import org.springframework.stereotype.Component;

@Component
public class GroupDiscount implements DiscountStrategy {
    @Override
    public BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening) {
        if (ticket.quantity() >= 3) {
            return currentPrice.multiply(BigDecimal.valueOf(0.9));
        }
        return currentPrice;
    }
}