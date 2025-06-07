package com.currencies.service;

import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.exception.exceptions.JsonParseException;
import com.currencies.nbpApiClient.ExchangeRateApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateProcessorTest {

    @Mock
    private ExchangeRateService service;

    @Mock
    private ExchangeRateApiClient exchangeRateApiClient;

    @InjectMocks
    private ExchangeRateProcessor processor;

    private String validJsonResponse;

    @BeforeEach
    void setUp() {
        validJsonResponse = """
            [
                {
                    "r030": 840,
                    "txt": "Dollar USA",
                    "rate": 36.5686,
                    "cc": "USD",
                    "exchangedate": "01.12.2023"
                }
            ]
            """;
    }

    @Test
    void updateCurrencyRates_ShouldProcessValidJsonSuccessfully() {
        // Given
        when(exchangeRateApiClient.fetchCurrencyRatesFromNBP()).thenReturn(validJsonResponse);

        // When
        processor.updateCurrencyRates();

        // Then
        verify(exchangeRateApiClient).fetchCurrencyRatesFromNBP();
        verify(service).saveCurrencyRates(anyList());
    }

    @Test
    void parseDataFromJson_ShouldParseValidJson() {
        // When
        List<ExchangeRateDto> result = processor.parseDataFromJson(validJsonResponse);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        ExchangeRateDto dto = result.get(0);
        assertEquals(840, dto.r030());
        assertEquals("Dollar USA", dto.txt());
        assertEquals(36.5686, dto.rate());
        assertEquals("USD", dto.cc());
        assertEquals("01.12.2023", dto.exchangedate());
    }

    @Test
    void parseDataFromJson_ShouldThrowJsonParseException_WhenInvalidJson() {
        // Given
        String invalidJson = "invalid json";

        // When/Then
        assertThrows(JsonParseException.class, () -> processor.parseDataFromJson(invalidJson));
    }
} 