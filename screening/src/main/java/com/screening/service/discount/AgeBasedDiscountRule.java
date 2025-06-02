package com.screening.service.discount;

import com.screening.domain.dto.DiscountContext;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Component
public class AgeBasedDiscountRule implements DiscountRule {

    private static final int CHILD_DISCOUNT = 20;

    private static final int SENIOR_DISCOUNT = 15;

    @Override
    public DiscountContext apply(DiscountContext context) {
        int age = Period.between(context.getRequest().getUser().getDateOfBirth(), LocalDate.now()).getYears();
        int apply = 0;
        String reason = "";

        if (age < 12) {
            apply = Math.min(CHILD_DISCOUNT, 40 - context.getTotalPercentage());
            reason = "Child discount (-" + apply + "%)";
        } else if (age >= 65) {
            apply = Math.min(SENIOR_DISCOUNT, 40 - context.getTotalPercentage());
            reason = "Senior discount (-" + apply + "%)";
        }

        if (apply > 0 && context.getTotalPercentage() < 40) {
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add(reason);
        }

        return context;
    }
}