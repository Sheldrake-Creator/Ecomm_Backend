package com.exception;

public class CartItemException extends Exception {

    // Default constructor
    public CartItemException() {
        super();
    }

    // Constructor with message
    public CartItemException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public CartItemException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public CartItemException(Throwable cause) {
        super(cause);
    }
}
