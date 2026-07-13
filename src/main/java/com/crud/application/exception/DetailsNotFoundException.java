package com.crud.application.exception;

public class DetailsNotFoundException extends RuntimeException {
    public DetailsNotFoundException(String message) {
        super(message);
    }
}
