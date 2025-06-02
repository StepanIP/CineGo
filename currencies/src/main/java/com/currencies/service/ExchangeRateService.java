package com.currencies.service;

import com.currencies.domain.ExchangeRate;
import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateRequest;
import com.currencies.domain.dto.ExchangeRateResponse;
import com.currencies.exception.exceptions.NotFoundException;
import com.currencies.mapper.CurrenciesMapper;
import com.currencies.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExchangeRateService {

    private final ExchangeRateRepository repository;

    private final CurrenciesMapper mapper;

    public ExchangeRateDto findRateByCode(String code) {
        ExchangeRate exchangeRate = repository.findByCode(code)
            .orElseThrow(() -> new NotFoundException("Not found %s code", code));
        return mapper.entityToExchangeRateDto(exchangeRate);
    }

    public List<ExchangeRateResponse> findAllCurrency() {
        log.info("Returning all currency");
        return repository.findAll().stream()
            .map(mapper::entityToResponseDto)
            .collect(Collectors.toList());
    }

    public void saveCurrencyRates(List<ExchangeRateDto> rates) {
        rates.forEach(rate -> {
            Optional<ExchangeRate> existingExchangeRate = repository.findByCode(rate.cc());
            if (existingExchangeRate.isPresent()) {
                ExchangeRate existing = existingExchangeRate.get();
                existing.setCurrency(rate.txt());
                existing.setCode(rate.cc());
                existing.setRate(rate.rate());
                repository.save(existing);
                log.info("Updated {} rate", existing.getCurrency());
            } else {
                ExchangeRate exchangeRate = new ExchangeRate(rate.txt(), rate.cc(), rate.rate());
                repository.save(exchangeRate);
                log.info("Added {} rate", exchangeRate.getCurrency());
            }
        });

        addUAHRate();
    }

    private void addUAHRate() {
        Optional<ExchangeRate> uahExchangeRate = repository.findByCode("UAH");
        if (uahExchangeRate.isEmpty()) {
            ExchangeRate exchangeRate = new ExchangeRate("Гривня", "UAH", 1.0);
            repository.save(exchangeRate);

            log.info("Added UAH (гривня) base rate");
        }
    }

    public ExchangeRateResponse exchangeRate(ExchangeRateRequest request) {
        ExchangeRate fromRate = repository.findByCode(request.getFrom())
            .orElseThrow(() -> new NotFoundException("Currency not found: %s", request.getFrom()));

        ExchangeRate toRate = repository.findByCode(request.getTo())
            .orElseThrow(() -> new NotFoundException("Currency not found: %s", request.getTo()));

        double uahAmount = request.getAmount() * fromRate.getRate();
        double convertedAmount = uahAmount / toRate.getRate();

        log.info("Converted {} {} to {} {}",
            request.getAmount(), fromRate.getCode(), convertedAmount, toRate.getCode());

        return new ExchangeRateResponse(toRate.getCurrency(), toRate.getCode());
    }

}
