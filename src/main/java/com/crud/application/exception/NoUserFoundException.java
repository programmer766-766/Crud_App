package com.crud.application.exception;

public class NoUserFoundException extends RuntimeException{
    public NoUserFoundException(String message){
        super(message);
    }
}
