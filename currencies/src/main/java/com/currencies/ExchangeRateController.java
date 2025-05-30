package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
class ExchangeRateController {

    private final ExchangeRateService service;

    @GetMapping("/api/v1/codes")
    public List<ExchangeRateResponseDto> findAllCurrencies() {
        return service.findAllCurrency();
    }

    @GetMapping("/api/v1/codes/{code}")
    public ExchangeRateDto findCode(@PathVariable String code) {
        return service.findRateByCode(code);
    }
}
