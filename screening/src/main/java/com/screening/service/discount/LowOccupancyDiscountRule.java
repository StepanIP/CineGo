package com.screening.service.discount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.screening.domain.dto.DiscountContext;
import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;
import com.screening.domain.model.SeatStatus;

import org.springframework.stereotype.Component;

@Component
public class LowOccupancyDiscountRule implements DiscountRule {

    private static final int LOW_OCCUPANCY_DISCOUNT = 5;

    @Override
    public DiscountContext apply(DiscountContext context) {
        if (!isTodayScreening(context.getScreening())) {
            return context;
        }

        double occupancyRate = getOccupancyRate(context);

        if (occupancyRate < 0.3 && context.getTotalPercentage() < 40) {
            int apply = Math.min(LOW_OCCUPANCY_DISCOUNT, 40 - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);

            if (context.getReasons() == null) {
                context.setReasons(new ArrayList<>());
            }
            context.getReasons().add("Low occupancy discount (-" + apply + "%)");
        }

        return context;
    }

    private boolean isTodayScreening(Screening screening) {
        LocalDate screeningDate = screening.getDate();
        return screeningDate.equals(LocalDate.now());
    }

    private double getOccupancyRate(DiscountContext context) {
        List<Seat> seats = context.getScreening().getSeats();

        long totalSeats = seats.size();
        if (totalSeats == 0) {
            return 0.0;
        }

        long takenSeats = seats.stream()
            .filter(seat -> seat.getStatus() == SeatStatus.TAKEN)
            .count();

        return (double) takenSeats / totalSeats;
    }
}
