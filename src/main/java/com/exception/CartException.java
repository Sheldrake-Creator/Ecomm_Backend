package com.exception;

public class CartException extends Exception {

    // Default constructor
    public CartException() {
        super();
    }

    // Constructor with message
    public CartException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public CartException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public CartException(Throwable cause) {
        super(cause);
    }

}
