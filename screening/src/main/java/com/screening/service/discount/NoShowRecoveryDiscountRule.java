package com.screening.service.discount;

import com.screening.client.TicketClient;
import com.screening.domain.dto.DiscountContext;
import com.screening.domain.model.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class NoShowRecoveryDiscountRule implements DiscountRule {

    private static final int DISCOUNT = 15;

    private final TicketClient ticketClient;

    @Override
    public DiscountContext apply(DiscountContext context) {
        var userId = context.getRequest().getUser().getId();
        var screeningDate = context.getScreening().getDate();
        var tickets = ticketClient.findTicketsByUserId(userId);

        var eligibleTicket = tickets.stream()
            .filter(ticket ->
                ticket.getStatus() == TicketStatus.EXPIRED &&
                    isSameMonth(ticket.getScreeningDate(), screeningDate)
            )
            .findFirst();

        if (eligibleTicket.isPresent() && context.getTotalPercentage() < 40) {
            int apply = Math.min(DISCOUNT, 40 - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("No-show recovery discount (-" + apply + "%)");

            ticketClient.updateTicketStatus(eligibleTicket.get().getId(), TicketStatus.USED_FOR_DISCOUNT);
        }

        return context;
    }

    private boolean isSameMonth(LocalDate d1, LocalDate d2) {
        return d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth();
    }
}
