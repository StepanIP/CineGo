package com.ticket.ticketingSystem.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
