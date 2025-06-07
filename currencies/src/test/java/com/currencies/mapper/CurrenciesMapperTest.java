package com.currencies.mapper;

import com.currencies.domain.ExchangeRate;
import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrenciesMapperTest {

    private final CurrenciesMapper mapper = Mappers.getMapper(CurrenciesMapper.class);

    @Test
    void entityToResponseDto_ShouldMapCorrectly() {
        // Given
        ExchangeRate exchangeRate = new ExchangeRate("Dollar USA", "USD", 36.5686);

        // When
        ExchangeRateResponse response = mapper.entityToResponseDto(exchangeRate);

        // Then
        assertNotNull(response);
        assertEquals(exchangeRate.getCurrency(), response.currency());
        assertEquals(exchangeRate.getCode(), response.code());
    }

    @Test
    void entityToExchangeRateDto_ShouldMapCorrectly() {
        // Given
        ExchangeRate exchangeRate = new ExchangeRate("Dollar USA", "USD", 36.5686);

        // When
        ExchangeRateDto dto = mapper.entityToExchangeRateDto(exchangeRate);

        // Then
        assertNotNull(dto);
        assertEquals(exchangeRate.getCurrency(), dto.txt());
        assertEquals(exchangeRate.getCode(), dto.cc());
        assertEquals(exchangeRate.getRate(), dto.rate());
    }
} 