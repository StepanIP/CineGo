package com.screening.service.discount;

import java.util.ArrayList;
import com.screening.domain.dto.DiscountContext;
import org.springframework.stereotype.Component;

import static com.screening.constant.Constants.MAX_TOTAL_DISCOUNT;

@Component
public class StudentDiscountRule implements DiscountRule {

    private static final int STUDENT_DISCOUNT = 15;

    @Override
    public DiscountContext apply(DiscountContext context) {
        if (context.getRequest().getUser().isStudent() &&
            context.getTotalPercentage() < MAX_TOTAL_DISCOUNT) {

            int apply = Math.min(STUDENT_DISCOUNT, MAX_TOTAL_DISCOUNT - context.getTotalPercentage());
            context.setTotalPercentage(context.getTotalPercentage() + apply);
            context.setReasons(context.getReasons() != null ? context.getReasons() : new ArrayList<>());
            context.getReasons().add("Student discount (-" + apply + "%)");
        }
        return context;
    }
}
