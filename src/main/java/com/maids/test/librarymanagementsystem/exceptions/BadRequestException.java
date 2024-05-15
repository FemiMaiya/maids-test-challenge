package com.maids.test.librarymanagementsystem.exceptions;

import lombok.Data;

import java.util.Map;

@Data
public class BadRequestException extends RuntimeException{
    private final String message;
    private final Map<String, Object> validation;

    public BadRequestException(String message, Map<String, Object> validation) {
        this.message = message;
        this.validation = validation;
    }
}
