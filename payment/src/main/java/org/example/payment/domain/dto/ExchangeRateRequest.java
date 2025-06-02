package org.example.payment.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateRequest {

    double amount;

    String from;

    String to;
}
