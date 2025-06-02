package com.ticket.ticketingSystem.dto;

import com.ticket.ticketingSystem.model.enums.AccountType;

public record UserResponseDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    AccountType accountType) {
}
