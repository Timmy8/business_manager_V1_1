package com.github.Timmy8.controller.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@ControllerAdvice
public class DefaultExceptionHandler {
    private final MessageSource messageSource;
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage(ex.getMessage(), null,
                        ex.getMessage(), locale));
    }
}
