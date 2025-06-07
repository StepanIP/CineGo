package com.currencies.service;

import com.currencies.domain.ExchangeRate;
import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateRequest;
import com.currencies.domain.dto.ExchangeRateResponse;
import com.currencies.exception.exceptions.NotFoundException;
import com.currencies.mapper.CurrenciesMapper;
import com.currencies.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository repository;

    @Mock
    private CurrenciesMapper mapper;

    @InjectMocks
    private ExchangeRateService service;

    private ExchangeRate exchangeRate;
    private ExchangeRateDto exchangeRateDto;
    private ExchangeRateResponse exchangeRateResponse;
    private List<ExchangeRate> exchangeRates;

    @BeforeEach
    void setUp() {
        exchangeRate = new ExchangeRate("Dollar USA", "USD", 36.5686);
        exchangeRateDto = new ExchangeRateDto(840, "Dollar USA", 36.5686, "USD", "01.12.2023");
        exchangeRateResponse = new ExchangeRateResponse("Dollar USA", "USD");
        exchangeRates = Arrays.asList(exchangeRate);
    }

    @Test
    void findRateByCode_WhenCodeExists_ShouldReturnDto() {
        // Given
        String code = "USD";
        when(repository.findByCode(code)).thenReturn(Optional.of(exchangeRate));
        when(mapper.entityToExchangeRateDto(exchangeRate)).thenReturn(exchangeRateDto);

        // When
        ExchangeRateDto result = service.findRateByCode(code);

        // Then
        assertEquals(exchangeRateDto, result);
        verify(repository).findByCode(code);
        verify(mapper).entityToExchangeRateDto(exchangeRate);
    }

    @Test
    void findRateByCode_WhenCodeDoesNotExist_ShouldThrowNotFoundException() {
        // Given
        String code = "XXX";
        when(repository.findByCode(code)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundException.class, () -> service.findRateByCode(code));
        verify(repository).findByCode(code);
        verifyNoInteractions(mapper);
    }

    @Test
    void findAllCurrency_ShouldReturnAllCurrencies() {
        // Given
        when(repository.findAll()).thenReturn(exchangeRates);
        when(mapper.entityToResponseDto(exchangeRate)).thenReturn(exchangeRateResponse);

        // When
        List<ExchangeRateResponse> result = service.findAllCurrency();

        // Then
        assertEquals(1, result.size());
        assertEquals(exchangeRateResponse, result.get(0));
        verify(repository).findAll();
        verify(mapper).entityToResponseDto(exchangeRate);
    }

    @Test
    void saveCurrencyRates_ShouldUpdateExistingAndAddNewRates() {
        // Given
        List<ExchangeRateDto> rates = Arrays.asList(exchangeRateDto);
        when(repository.findByCode("USD")).thenReturn(Optional.of(exchangeRate));
        when(repository.findByCode("UAH")).thenReturn(Optional.empty());

        // When
        service.saveCurrencyRates(rates);

        // Then
        verify(repository, times(2)).save(any(ExchangeRate.class));
        verify(repository).findByCode("USD");
        verify(repository).findByCode("UAH");
    }

    @Test
    void exchangeRate_ShouldCalculateCorrectly() {
        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .amount(100.0)
                .from("USD")
                .to("EUR")
                .build();

        ExchangeRate usdRate = new ExchangeRate("Dollar USA", "USD", 36.5686);
        ExchangeRate eurRate = new ExchangeRate("Euro", "EUR", 40.0);

        when(repository.findByCode("USD")).thenReturn(Optional.of(usdRate));
        when(repository.findByCode("EUR")).thenReturn(Optional.of(eurRate));

        // When
        ExchangeRateResponse result = service.exchangeRate(request);

        // Then
        assertNotNull(result);
        assertEquals("Euro", result.currency());
        assertEquals("EUR", result.code());
        verify(repository).findByCode("USD");
        verify(repository).findByCode("EUR");
    }

    @Test
    void exchangeRate_WhenCurrencyNotFound_ShouldThrowNotFoundException() {
        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .amount(100.0)
                .from("XXX")
                .to("EUR")
                .build();

        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundException.class, () -> service.exchangeRate(request));
        verify(repository).findByCode("XXX");
        verifyNoMoreInteractions(repository);
    }
} 