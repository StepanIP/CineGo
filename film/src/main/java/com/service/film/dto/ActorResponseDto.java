package com.service.film.dto;

public record ActorResponseDto(
    Long id,
    String firstName,
    String lastName,
    String photo
) {}