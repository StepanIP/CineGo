package com.user.domain.dto;

import lombok.Builder;

@Builder
public record UserDto(
    Long id,
    String username,
    String email,
    String role
) {}