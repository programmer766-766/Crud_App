package com.crud.application.controller;

import com.crud.application.exception.DetailsNotFoundException;
import com.crud.application.exception.NoPanDataAvailableException;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.exception.PinCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
    @ExceptionHandler({NoUserFoundException.class,DetailsNotFoundException.class, NoPanDataAvailableException.class, PinCodeNotFoundException.class})
    public ResponseEntity<Map<String,Object>> noUserFoundException(RuntimeException exception){
        Map<String,Object> error=new HashMap<>();
        error.put("Exception name",exception.getClass().getSimpleName());
        error.put("Message",exception.getMessage());
        error.put("Status name", HttpStatus.NOT_FOUND);
        error.put("Status code",HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String,Object>> authenticationException(AuthenticationException exception){
        Map<String,Object> error=new HashMap<>();
        error.put("Exception name",exception.getClass().getSimpleName());
        error.put("Message",exception.getMessage());
        error.put("Status name", HttpStatus.UNAUTHORIZED);
        error.put("Status code",HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }


}
