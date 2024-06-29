package com.exception;

public class ProductException extends ServiceException {

    // Default constructor
    public ProductException() {
        super();
    }

    // Constructor with message
    public ProductException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ProductException(Throwable cause) {
        super(cause);
    }
}
