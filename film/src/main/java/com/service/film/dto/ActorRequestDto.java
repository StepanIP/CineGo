package com.service.film.dto;

import java.time.LocalDate;

public record ActorRequestDto(
    String firstName,
    String lastName,
    LocalDate birthDate,
    String photo
) {}
