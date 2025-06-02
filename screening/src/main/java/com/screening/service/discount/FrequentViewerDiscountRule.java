package com.screening.service.discount;

import java.util.ArrayList;

import com.screening.client.TicketClient;
import com.screening.domain.dto.DiscountContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static com.screening.constant.Constants.MAX_TOTAL_DISCOUNT;

@Component
@RequiredArgsConstructor
public class FrequentViewerDiscountRule implements DiscountRule {

    private final TicketClient ticketClient;

    @Override
    public DiscountContext apply(DiscountContext context) {
        long monthlyViews = getMonthlyViewCount(context);
        int discount = 0;

        if (monthlyViews >= 10) {
            discount = 15;
        } else if (monthlyViews >= 5) {
            discount = 10;
        } else if (monthlyViews >= 2) {
            discount = 5;
        }

        if (discount > 0 && context.getTotalPercentage() < MAX_TOTAL_DISCOUNT) {
            int apply = Math.min(discount, MAX_TOTAL_DISCOUNT - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Frequent viewer discount (-" + apply + "%)");
        }

        return context;
    }

    private long getMonthlyViewCount(DiscountContext context) {
        return ticketClient
            .findTicketsByUserId(context.getRequest().getUser().getId())
            .stream()
            .filter(ticket -> ticket.getScreeningDate()
                .isAfter(context.getScreening().getDate().minusMonths(1)))
            .count();
    }
}

