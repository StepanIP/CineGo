package com.screening.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountRequest {

    private Long screeningId;

    private Long seatId;

    private UserRequest user;
}
