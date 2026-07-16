package com.crud.application.exception;

public class PinCodeNotFoundException extends RuntimeException {
    public PinCodeNotFoundException(String message) {
        super(message);
    }
}
