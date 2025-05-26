package com.currencies.dto;

public record ExchangeRateDto(
    int r030,
    String txt,
    double rate,
    String cc,
    String exchangedate
) {
}