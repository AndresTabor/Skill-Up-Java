package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateDto {

    @NotNull(message = "{currency.notnull")
    private Currency currency;
}
