package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserException extends ServiceException {

    // Default constructor
    public UserException() {
        super();
    }

    // Constructor with message
    public UserException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public UserException(Throwable cause) {
        super(cause);
    }

}
