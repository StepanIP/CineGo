package com.ticket.ticketingSystem.ticketPriceCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.ticket.common.dto.ExchangeRate;
import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;
import com.ticket.feignClient.CurrenciesClient;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import static com.ticket.ticketingSystem.ticketPriceCalculator.TicketPrice.BASIC_TICKET_PRICE;

@Component
@AllArgsConstructor
public class TicketPriceCalculator {

    private final CurrenciesClient currenciesClient;
    private final List<DiscountStrategy> discountStrategies;

    public BigDecimal finalPrice(TicketBookingDto ticket, ScreeningDto screening) {
        ExchangeRate exchangeRate = currenciesClient.findCode(ticket.currency().toString());

        BigDecimal price = BigDecimal.valueOf(BASIC_TICKET_PRICE);

        for (DiscountStrategy strategy : discountStrategies) {
            price = strategy.apply(price, ticket, screening);
        }

        return price
            .divide(BigDecimal.valueOf(exchangeRate.rate()), 1, RoundingMode.HALF_UP)
            .max(BigDecimal.ZERO);
    }
}
