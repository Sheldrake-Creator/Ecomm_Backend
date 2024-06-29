package com.exception;

public class ReviewException extends ServiceException {

    // Default constructor
    public ReviewException() {
        super();
    }

    // Constructor with message
    public ReviewException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ReviewException(Throwable cause) {
        super(cause);
    }
}
