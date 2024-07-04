package com.exception;

public class ProductServiceException extends ServiceException {

    // Default constructor
    public ProductServiceException() {
        super();
    }

    // Constructor with message
    public ProductServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ProductServiceException(Throwable cause) {
        super(cause);
    }
}
