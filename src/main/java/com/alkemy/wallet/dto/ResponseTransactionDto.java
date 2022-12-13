package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.enums.TypeOfTransaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseTransactionDto {

    private Long id;

    private Double amount;

    private Date transactionDate;

    private TypeOfTransaction type;

    private String description;

    private AccountDto account;

}
