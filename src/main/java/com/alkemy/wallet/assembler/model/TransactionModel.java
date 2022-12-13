package com.alkemy.wallet.assembler.model;

import com.alkemy.wallet.dto.BasicAccountDto;
import com.alkemy.wallet.model.enums.TypeOfTransaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Hidden
@Setter
@Getter
public class TransactionModel extends RepresentationModel<TransactionModel> {

    private Long id;

    private Double amount;

    private TypeOfTransaction type;

    private String description;

    private BasicAccountDto account;

}
