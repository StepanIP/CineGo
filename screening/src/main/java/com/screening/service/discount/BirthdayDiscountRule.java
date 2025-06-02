package com.screening.service.discount;

import java.time.LocalDate;
import java.util.ArrayList;

import com.screening.domain.dto.DiscountContext;

import org.springframework.stereotype.Component;

@Component
public class BirthdayDiscountRule implements DiscountRule {

    private static final int BIRTHDAY_DISCOUNT = 20;

    @Override
    public DiscountContext apply(DiscountContext context) {
        LocalDate birthday = context.getRequest().getUser().getDateOfBirth();
        LocalDate today = LocalDate.now();

        if (birthday != null &&
            birthday.getMonth() == today.getMonth() &&
            birthday.getDayOfMonth() == today.getDayOfMonth() &&
            context.getTotalPercentage() < 40) {

            int apply = Math.min(BIRTHDAY_DISCOUNT, 40 - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Birthday discount (-" + apply + "%)");
        }
        return context;
    }
}
