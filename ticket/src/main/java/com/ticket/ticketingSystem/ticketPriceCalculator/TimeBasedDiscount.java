package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;

import com.ticket.ticketingSystem.dto.ScreeningDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;

import org.springframework.stereotype.Component;

@Component
public class TimeBasedDiscount implements DiscountStrategy {

    @Override
    public BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening) {
//        int hour = screening.date().getHour();
//        if (hour < 12) {
//            return currentPrice.multiply(BigDecimal.valueOf(0.8)); // -20%
//        } else if (hour >= 22) {
//            return currentPrice.multiply(BigDecimal.valueOf(0.85)); // -15%
//        }
        return currentPrice;
    }
}
