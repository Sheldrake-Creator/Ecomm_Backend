package com.exception;

public class AddressServiceException extends ServiceException {

    // Default constructor
    public AddressServiceException() {
        super();
    }

    // Constructor with message
    public AddressServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public AddressServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public AddressServiceException(Throwable cause) {
        super(cause);
    }

}
