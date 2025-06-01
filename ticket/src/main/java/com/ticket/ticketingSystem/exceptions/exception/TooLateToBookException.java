package com.ticket.ticketingSystem.exceptions.exception;

public class TooLateToBookException extends  RuntimeException {

    public TooLateToBookException(final String message) {
        super(message);
    }

}
