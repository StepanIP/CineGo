package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import com.currencies.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
class ExchangeRateService {

    private final ExchangeRateRepository repository;

    private final CurrenciesMapper mapper;

    public ExchangeRateDto findRateByCode(String code) {
        ExchangeRate exchangeRate = repository.findByCode(code)
            .orElseThrow(() -> new NotFoundException("Not found %s code", code));
        return mapper.entityToExchangeRateDto(exchangeRate);
    }

    public List<ExchangeRateResponseDto> findAllCurrency() {
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
}
