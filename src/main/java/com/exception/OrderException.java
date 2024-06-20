package com.exception;

public class OrderException extends Exception {

    // Default constructor
    public OrderException() {
        super();
    }

    // Constructor with message
    public OrderException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public OrderException(Throwable cause) {
        super(cause);
    }
}
