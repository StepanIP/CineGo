package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

@SpringBootTest
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService service;

    @Mock
    private ExchangeRateRepository repository;

    @Mock
    private CurrenciesMapper currenciesMapper;

    @Mock
    private ExchangeRate exchangeRate;

    @Mock
    private ExchangeRateDto exchangeRateDto;

    @BeforeEach
    void setUp() {
        exchangeRate = new ExchangeRate(1L, "Dolar", "USD", 3.66);
        exchangeRateDto = new ExchangeRateDto(1, "Euro", 4.22, "EUR", "2025-10-01");
    }

    @Test
    @DisplayName("Should find currency by code")
    void should_find_currency_by_code() {
        String code = "USD";
        given(repository.findByCode(code)).willReturn(Optional.of(exchangeRate));
        given(currenciesMapper.entityToExchangeRateDto(exchangeRate))
            .willReturn(exchangeRateDto);

        assertThat(service.findRateByCode(code)).isEqualTo(exchangeRateDto);
    }
}


