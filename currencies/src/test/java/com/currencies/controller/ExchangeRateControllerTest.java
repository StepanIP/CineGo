package com.currencies.controller;

import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateRequest;
import com.currencies.domain.dto.ExchangeRateResponse;
import com.currencies.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    @Mock
    private ExchangeRateService service;

    @InjectMocks
    private ExchangeRateController controller;

    private ExchangeRateDto exchangeRateDto;
    private ExchangeRateResponse exchangeRateResponse;
    private ExchangeRateRequest exchangeRateRequest;

    @BeforeEach
    void setUp() {
        exchangeRateDto = new ExchangeRateDto(840, "Dollar USA", 36.5686, "USD", "01.12.2023");
        exchangeRateResponse = new ExchangeRateResponse("Dollar USA", "USD");
        exchangeRateRequest = ExchangeRateRequest.builder()
                .amount(100.0)
                .from("USD")
                .to("EUR")
                .build();
    }

    @Test
    void findAllCurrencies_ShouldReturnListOfCurrencies() {
        // Given
        List<ExchangeRateResponse> expectedResponses = Arrays.asList(exchangeRateResponse);
        when(service.findAllCurrency()).thenReturn(expectedResponses);

        // When
        List<ExchangeRateResponse> actualResponses = controller.findAllCurrencies();

        // Then
        assertEquals(expectedResponses, actualResponses);
        verify(service).findAllCurrency();
    }

    @Test
    void findCode_ShouldReturnExchangeRateDto() {
        // Given
        String code = "USD";
        when(service.findRateByCode(code)).thenReturn(exchangeRateDto);

        // When
        ExchangeRateDto actualDto = controller.findCode(code);

        // Then
        assertEquals(exchangeRateDto, actualDto);
        verify(service).findRateByCode(code);
    }

    @Test
    void exchangeRate_ShouldReturnExchangeRateResponse() {
        // Given
        when(service.exchangeRate(exchangeRateRequest)).thenReturn(exchangeRateResponse);

        // When
        ExchangeRateResponse actualResponse = controller.exchangeRate(exchangeRateRequest);

        // Then
        assertEquals(exchangeRateResponse, actualResponse);
        verify(service).exchangeRate(exchangeRateRequest);
    }
} 