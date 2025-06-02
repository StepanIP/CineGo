package com.screening.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountContext {

    private DiscountRequest request;

    private Screening screening;

    private Seat seat;

    private BigDecimal totalDiscount;

    private int totalPercentage;

    private List<String> reasons;
}
