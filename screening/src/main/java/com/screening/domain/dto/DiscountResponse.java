package com.screening.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DiscountResponse {

    BigDecimal discount;

    int discountPercentage;

    List<String> reasons;
}
