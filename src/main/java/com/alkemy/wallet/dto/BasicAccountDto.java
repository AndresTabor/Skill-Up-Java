package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.enums.Currency;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicAccountDto {

    private Long id;

    @NotNull
    private Currency currency;

    private Double transactionLimit;

    private Double balance;

    private Long userId;

    public BasicAccountDto(Currency currency) {
        this.currency = currency;
    }
}

