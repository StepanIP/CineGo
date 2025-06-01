package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;

import com.ticket.ticketingSystem.dto.ScreeningDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;
import com.ticket.ticketingSystem.model.enums.TicketType;

import org.springframework.stereotype.Component;

import static com.ticket.ticketingSystem.ticketPriceCalculator.TicketPrice.STUDENT_REDUCE;

@Component
public class StudentDiscount implements DiscountStrategy {

    @Override
    public BigDecimal apply(BigDecimal currentPrice, TicketBookingDto ticket, ScreeningDto screening) {
        if (ticket.ticketType() == TicketType.REDUCE) {
            return currentPrice.subtract(BigDecimal.valueOf(STUDENT_REDUCE));
        }
        return currentPrice;
    }
}
