package com.authorization.jwtAuthorization.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public Map<String,String> alreadyExistException(RuntimeException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("ErrorMessage", exception.getMessage());
        return errorMap;

    }

    @ExceptionHandler(Exception.class)
    public Map<String,String> alreadyExistException(Exception exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("ErrorMessage", exception.getMessage());
        return errorMap;

    }
}
