package com.crud.application.controller;

import com.crud.application.exception.DetailsNotFoundException;
import com.crud.application.exception.NoUserFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
GlobalExceptionHandler class to handle custom exception in an Application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    Below method to handle NoUserFoundException
     */
    @ExceptionHandler({NoUserFoundException.class,DetailsNotFoundException.class})
    public ResponseEntity<Map<String,Object>> noUserFoundException(RuntimeException exception){
        Map<String,Object> error=new HashMap<>();
        error.put("Exception name",exception.getClass().getSimpleName());
        error.put("Message",exception.getMessage());
        error.put("Status name", HttpStatus.NOT_FOUND);
        error.put("Status code",HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
