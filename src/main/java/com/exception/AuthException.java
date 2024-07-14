package com.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException{
    private final HttpStatus httpStatus;

    public AuthException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

}
