package com.alkemy.wallet.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Hidden
@AllArgsConstructor
@Getter
@Builder
public class ApiException {

    private HttpStatus status;
    private String message;
    private String developerMessage;

}