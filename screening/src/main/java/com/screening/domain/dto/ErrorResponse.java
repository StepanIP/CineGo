package com.screening.domain.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
