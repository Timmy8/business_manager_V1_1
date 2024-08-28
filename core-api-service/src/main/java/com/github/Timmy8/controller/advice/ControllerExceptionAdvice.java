package com.github.Timmy8.controller.advice;

import com.github.Timmy8.controller.exception.PhoneNumberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@ControllerAdvice
public class ControllerExceptionAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandler(BindException ex){
        return ResponseEntity
                .badRequest()
                .body(ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<?> PhoneNumberAlreadyExistsExceptionHandler(PhoneNumberAlreadyExistsException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> dateTimeParseExceptionHandler(DateTimeParseException ex, Locale locale){
        String message = "api.appointment.create.errors.visit_date_parse_error";
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(message,
                        null, message, locale));
    }
}
