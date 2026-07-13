package com.crud.application.exception;

public class NoPanDataAvailableException extends RuntimeException{
    public NoPanDataAvailableException(String message){
        super(message);
    }
}
