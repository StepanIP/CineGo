package com.screening.exceptions;

public class TooManyScreeningException extends RuntimeException {

    public TooManyScreeningException(final String message) {
        super(message);
    }
}
