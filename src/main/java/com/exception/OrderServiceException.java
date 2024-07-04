package com.exception;

public class OrderServiceException extends ServiceException {

    // Default constructor
    public OrderServiceException() {
        super();
    }

    // Constructor with message
    public OrderServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public OrderServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public OrderServiceException(Throwable cause) {
        super(cause);
    }
}
