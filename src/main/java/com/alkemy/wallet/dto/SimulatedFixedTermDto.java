package com.alkemy.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SimulatedFixedTermDto {

    private Double amount;

    private Double interest;

    private Double totalAmount;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate creationDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate closingDate;

}
