package org.example.api;

import org.example.exception.NotFoundException;
import org.example.exception.NullPointerException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto processNotFoundException(NotFoundException exception) {
        return ErrorDto.of(exception.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public ErrorDto processNullPointerException(NullPointerException exception) {
        return ErrorDto.of(exception.getMessage());
    }
}

