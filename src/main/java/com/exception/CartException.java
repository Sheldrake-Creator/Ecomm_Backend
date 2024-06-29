package com.exception;

import com.dto.UserDTO;

public class CartException extends ServiceException {

    // Default constructor
    public CartException() {
        super();
    }

    // Constructor with message
    public CartException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public CartException(String message, UserDTO user) {
        super(message);
    }

    // Constructor with cause
    public CartException(Throwable cause) {
        super(cause);
    }

    // Constructor with cause
    public CartException(String message, Throwable cause) {
        super(message, cause);
    }

}
