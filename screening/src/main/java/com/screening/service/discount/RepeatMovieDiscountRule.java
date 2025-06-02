package com.screening.service.discount;

import java.util.ArrayList;
import java.util.List;

import com.screening.client.FilmClient;
import com.screening.client.TicketClient;
import com.screening.domain.dto.DiscountContext;
import com.screening.domain.dto.TicketResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static com.screening.constant.Constants.MAX_TOTAL_DISCOUNT;

@Component
@RequiredArgsConstructor
public class RepeatMovieDiscountRule implements DiscountRule {

    private static final int REPEAT_MOVIE_DISCOUNT = 5;

    private final TicketClient ticketClient;

    private final FilmClient filmClient;

    @Override
    public DiscountContext apply(DiscountContext context) {
        if (this.hasWatchedMovieBefore(context) &&
            context.getTotalPercentage() < MAX_TOTAL_DISCOUNT) {

            int apply = Math.min(REPEAT_MOVIE_DISCOUNT, MAX_TOTAL_DISCOUNT - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Repeat movie discount (-" + apply + "%)");
        }
        return context;
    }

    private boolean hasWatchedMovieBefore(DiscountContext context) {
        List<TicketResponseDto> tickets = ticketClient.findTicketsByUserId(context.getRequest().getUser().getId());
        return tickets.stream()
            .anyMatch(ticket -> ticket.getFilmTitle()
                .equals(filmClient.findById(context.getScreening().getFilmId())));
    }
}
