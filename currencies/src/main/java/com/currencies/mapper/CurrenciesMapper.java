package com.currencies.mapper;

import com.currencies.domain.ExchangeRate;
import com.currencies.domain.dto.ExchangeRateDto;
import com.currencies.domain.dto.ExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrenciesMapper {

    ExchangeRateResponse entityToResponseDto(ExchangeRate exchangeRate);

    @Mapping(target = "cc", source = "code")
    @Mapping(target = "txt", source = "currency")
    ExchangeRateDto entityToExchangeRateDto(ExchangeRate exchangeRate);

}
