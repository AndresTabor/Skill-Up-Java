package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class NoAmountException extends RuntimeException {
    public NoAmountException() {
        super();
    }

    public NoAmountException(String message) {
        super(message);
    }
}
