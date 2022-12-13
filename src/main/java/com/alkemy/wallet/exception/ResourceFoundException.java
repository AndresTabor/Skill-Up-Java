package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class ResourceFoundException extends RuntimeException {

    public ResourceFoundException() {
        super();
    }

    public ResourceFoundException(final String message) {
        super(message);
    }

    public ResourceFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
