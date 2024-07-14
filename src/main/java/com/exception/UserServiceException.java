package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserServiceException extends ServiceException {

    // Default constructor
    public UserServiceException() {
        super();
    }

    // Constructor with message
    public UserServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public UserServiceException(Throwable cause) {
        super(cause);
    }

}
