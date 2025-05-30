package com.screening.exceptions;

public class TooLateException extends RuntimeException {

    public TooLateException(final String message) {
        super(message);
    }

}

