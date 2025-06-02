package com.currencies.controller;

import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateRequest;
import com.currencies.domain.dto.ExchangeRateResponse;
import com.currencies.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/codes")
public class ExchangeRateController {

    private final ExchangeRateService service;

    @GetMapping
    public List<ExchangeRateResponse> findAllCurrencies() {
        return service.findAllCurrency();
    }

    @GetMapping("{code}")
    public ExchangeRateDto findCode(@PathVariable String code) {
        return service.findRateByCode(code);
    }

    @PostMapping("/exchange-rate")
    public ExchangeRateResponse exchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {
        return service.exchangeRate(exchangeRateRequest);
    }
}
