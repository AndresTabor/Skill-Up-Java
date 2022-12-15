package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
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
