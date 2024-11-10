package com.github.Timmy8.controller.advice;

import com.github.Timmy8.controller.exception.PhoneNumberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@ControllerAdvice
public class ControllerExceptionAdvice {
    private final Logger logger = LogManager.getLogger(ControllerExceptionAdvice.class.getName());
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandler(BindException ex){
        logger.warn("Binding exception:\n{}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException ex, Locale locale){
        logger.warn("NoSuchElementException:\n{}", messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<?> PhoneNumberAlreadyExistsExceptionHandler(PhoneNumberAlreadyExistsException ex, Locale locale){
        logger.warn("PhoneNumberAlreadyExistsException:\n{}", messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> dateTimeParseExceptionHandler(DateTimeParseException ex, Locale locale){
        String message = "api.appointment.create.errors.visit_date_parse_error";
        logger.warn("DateTimeParseException:\n{}", messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH));
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(message, null, message, locale));
    }
}
