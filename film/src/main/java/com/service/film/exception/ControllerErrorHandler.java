package com.service.film.exception;

import com.service.film.exception.exceptions.AlreadyExistException;
import com.service.film.exception.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse notFound(NotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseBody
    public ErrorResponse existException(AlreadyExistException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }
}

