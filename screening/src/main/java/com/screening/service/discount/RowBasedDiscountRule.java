package com.screening.service.discount;

import com.screening.domain.dto.DiscountContext;
import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RowBasedDiscountRule implements DiscountRule {

    private static final int ROW_DISCOUNT = 5;

    @Override
    public DiscountContext apply(DiscountContext context) {
        int row = context.getSeat().getRowsNumber();
        Screening screening = context.getScreening();

        int totalRows = screening.getSeats().stream()
                .mapToInt(Seat::getRowsNumber)
                .max()
                .orElse(0);

        if ((row == 1 || row == totalRows) && context.getTotalPercentage() < 40) {
            int apply = Math.min(ROW_DISCOUNT, 40 - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Row-based discount (-" + apply + "%)");
        }

        return context;
    }
}
