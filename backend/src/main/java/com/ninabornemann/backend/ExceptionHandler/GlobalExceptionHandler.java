package com.ninabornemann.backend.ExceptionHandler;

import com.ninabornemann.backend.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResponseStatusException(ResponseStatusException e) {
        return new ErrorMessage(e.getMessage(), Instant.now());
    }

}
