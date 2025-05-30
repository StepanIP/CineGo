package com.screening.exceptions;

public class AlreadyTakenException extends RuntimeException {

    public AlreadyTakenException(final String message) {
        super(message);
    }
}
