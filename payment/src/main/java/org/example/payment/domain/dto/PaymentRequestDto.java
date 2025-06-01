package org.example.payment.domain.dto;

public record PaymentRequestDto(
    double total,
    String currency,
    String description,
    Long ticketId
) {}