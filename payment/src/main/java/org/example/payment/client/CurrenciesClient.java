package org.example.payment.client;

import org.example.payment.domain.dto.ExchangeRateRequest;
import org.example.payment.domain.dto.ExchangeRateResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "currencies-service", url = "${application.config.currencies-url}")
public interface CurrenciesClient {

    @PostMapping("/exchange-rate")
    ExchangeRateResponse convert(ExchangeRateRequest request);
}
