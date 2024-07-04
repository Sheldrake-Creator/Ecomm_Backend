package com.exception;

public class RatingServiceException extends ServiceException {

    // Default constructor
    public RatingServiceException() {
        super();
    }

    // Constructor with message
    public RatingServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public RatingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public RatingServiceException(Throwable cause) {
        super(cause);
    }

}
