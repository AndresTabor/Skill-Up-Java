package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.enums.Currency;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {

    private Long id;

    private Double balance;

    private Currency currency;

    private List<FixedTermDto> fixedTerm;

}
