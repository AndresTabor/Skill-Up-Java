package com.alkemy.wallet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseTransactionDto {

    private Long id;

    private Double amount;

    private Date transactionDate;

    private String description;

    private AccountDto account;

}
