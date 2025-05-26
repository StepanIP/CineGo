package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.exception.exceptions.JsonParseException;
import com.currencies.nbpApiClient.ExchangeRateApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ExchangeRateProcessor {

    private final ExchangeRateService service;

    private final ExchangeRateApiClient exchangeRateApiClient;

    public void updateCurrencyRates() {
        String jsonResponse = exchangeRateApiClient.fetchCurrencyRatesFromNBP();
        List<ExchangeRateDto> currencyDataList = parseDataFromJson(jsonResponse);
        service.saveCurrencyRates(currencyDataList);
    }

    public List<ExchangeRateDto> parseDataFromJson(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new JsonParseException("Error parsing JSON response", e);
        }
    }
}
