package com.alkemy.wallet.exception;

public class UserNotAllowed extends RuntimeException {
    public UserNotAllowed() {
        super();
    }

    public UserNotAllowed(final String message) {
        super(message);
    }

    public UserNotAllowed(final String message, final Throwable cause) {
        super(message, cause);
    }

}
