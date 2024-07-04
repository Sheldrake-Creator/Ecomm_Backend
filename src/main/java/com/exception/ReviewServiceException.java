package com.exception;

public class ReviewServiceException extends ServiceException {

    // Default constructor
    public ReviewServiceException() {
        super();
    }

    // Constructor with message
    public ReviewServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ReviewServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ReviewServiceException(Throwable cause) {
        super(cause);
    }
}
