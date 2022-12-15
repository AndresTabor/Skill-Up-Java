package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class UserNotLoggedException extends RuntimeException {
    public UserNotLoggedException() {
        super();
    }

    public UserNotLoggedException(String message) {
        super(message);
    }
}
