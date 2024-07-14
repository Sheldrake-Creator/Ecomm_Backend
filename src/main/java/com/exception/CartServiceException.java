package com.exception;

import com.dto.UserDTO;

public class CartServiceException extends ServiceException {

    // Default constructor
    public CartServiceException() {
        super();
    }

    // Constructor with message
    public CartServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public CartServiceException(String message, UserDTO user) {
        super(message);
    }

    // Constructor with cause
    public CartServiceException(Throwable cause) {
        super(cause);
    }

    // Constructor with cause
    public CartServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
