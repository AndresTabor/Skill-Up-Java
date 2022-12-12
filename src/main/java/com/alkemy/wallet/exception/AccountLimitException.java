package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class AccountLimitException extends RuntimeException {
    public AccountLimitException() {
        super();
    }

    public AccountLimitException(String message) {
        super(message);
    }
}
