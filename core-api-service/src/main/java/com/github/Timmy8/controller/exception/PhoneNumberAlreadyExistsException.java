package com.github.Timmy8.controller.exception;

import java.util.NoSuchElementException;

public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException() {
    }

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}
