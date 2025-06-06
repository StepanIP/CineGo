package com.screening.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }

}
