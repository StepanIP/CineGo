package com.ticket.ticketingSystem.ticketPriceCalculator;

import com.ticket.ticketingSystem.dto.ScreeningDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class EarlyBookingDiscount implements DiscountStrategy {
    @Override
    public BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening) {
        Duration diff = Duration.between(LocalDateTime.now(), screening.date());
        if (diff.toDays() >= 5) {
            return currentPrice.multiply(BigDecimal.valueOf(0.9));
        }
        return currentPrice;
    }
}