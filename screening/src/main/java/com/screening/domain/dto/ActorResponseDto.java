package com.screening.domain.dto;

public record ActorResponseDto(
    Long id,
    String firstName,
    String lastName,
    String photo
) {}