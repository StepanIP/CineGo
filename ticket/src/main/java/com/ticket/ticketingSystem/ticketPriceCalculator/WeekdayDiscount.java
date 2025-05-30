package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;

import org.springframework.stereotype.Component;

@Component
public class WeekdayDiscount implements DiscountStrategy {

    @Override
    public BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening) {
        DayOfWeek day = screening.date().getDayOfWeek();
        if (day == DayOfWeek.TUESDAY || day == DayOfWeek.WEDNESDAY) {
            return currentPrice.multiply(BigDecimal.valueOf(0.8)); // 20% знижка
        }
        return currentPrice;
    }
}
