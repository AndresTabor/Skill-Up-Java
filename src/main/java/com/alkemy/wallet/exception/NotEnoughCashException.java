package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException() {
        super();
    }

    public NotEnoughCashException(String message) {
        super(message);
    }
}
