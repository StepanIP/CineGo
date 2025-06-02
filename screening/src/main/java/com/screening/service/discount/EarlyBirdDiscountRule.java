package com.screening.service.discount;

import java.util.ArrayList;

import com.screening.domain.dto.DiscountContext;

import org.springframework.stereotype.Component;

@Component
public class EarlyBirdDiscountRule implements DiscountRule {

    private static final int EARLY_BIRD_DISCOUNT = 10;

    @Override
    public DiscountContext apply(DiscountContext context) {
        if (this.getDaysBeforeScreening(context) >= 7 &&
            context.getTotalPercentage() < 40) {

            int apply = Math.min(EARLY_BIRD_DISCOUNT, 40 - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Early bird discount (-" + apply + "%)");
        }
        return context;
    }

    private int getDaysBeforeScreening(DiscountContext context) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
            java.time.LocalDate.now(),
            context.getScreening().getDate()
        );
    }
}
