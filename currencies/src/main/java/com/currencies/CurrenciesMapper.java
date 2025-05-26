package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrenciesMapper {

    ExchangeRateResponseDto entityToResponseDto(ExchangeRate exchangeRate);

    @Mapping(target = "cc", source = "code")
    ExchangeRateDto entityToExchangeRateDto(ExchangeRate exchangeRate);

}
