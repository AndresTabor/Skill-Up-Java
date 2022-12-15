package com.alkemy.wallet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RequestTransactionDto {

    private Double amount;

    private String description;

    private AccountDto account;

}
