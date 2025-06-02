package com.screening.service.discount;

import com.screening.domain.dto.DiscountContext;

public interface DiscountRule {

    DiscountContext apply(DiscountContext context);
}