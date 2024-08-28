package com.github.Timmy8.restClient.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BadRequestException extends RuntimeException{
    private final List<String> errors;
}
