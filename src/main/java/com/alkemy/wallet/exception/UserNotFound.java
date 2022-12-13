package com.alkemy.wallet.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound() {
        super();
    }

    public UserNotFound(final String message) {
        super(message);
    }

    public UserNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

}
