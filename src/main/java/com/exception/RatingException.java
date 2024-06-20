package com.exception;

public class RatingException extends Exception {

    // Default constructor
    public RatingException() {
        super();
    }

    // Constructor with message
    public RatingException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public RatingException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public RatingException(Throwable cause) {
        super(cause);
    }

}
