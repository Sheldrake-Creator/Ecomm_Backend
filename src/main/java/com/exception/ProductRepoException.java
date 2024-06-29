package com.exception;

public class ProductRepoException extends RepositoryException {

    ProductRepoException() {
        super();
    }

    ProductRepoException(String message) {
        super(message);
    }

    ProductRepoException(Throwable cause) {
        super(cause);
    }

    ProductRepoException(String message, Throwable cause) {
        super(message, cause);
    }

}
