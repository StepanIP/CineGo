package com.screening.domain.dto;

import java.time.LocalDate;

public record UserResponseDto(
    Long id,
    String username,
    String email,
    LocalDate dateOfBirth
) {} 