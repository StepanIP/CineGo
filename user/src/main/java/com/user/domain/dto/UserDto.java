package com.user.domain.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record UserDto(
    Long id,
    String username,
    String email,
    String role,
    LocalDate birthDate,
    boolean isStudent
) {}