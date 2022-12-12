package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedTermDto {

    private Long id;

    @NotNull(message = "{amount.notnull}")
    private Double amount;

    private Long accountId;

    private Double interest;

    @NotNull(message = "{creationdate.notnull}")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @NotNull(message = "{closingdate.notnull}")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate closingDate;

    @NotNull(message = "{currency.notnull}")
    private Currency currency;

}
