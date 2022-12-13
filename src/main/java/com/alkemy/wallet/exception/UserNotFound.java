package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
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
