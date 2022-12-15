package com.alkemy.wallet.assembler.model;

import com.alkemy.wallet.dto.BasicAccountDto;
import com.alkemy.wallet.model.enums.TypeOfTransaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Hidden
@Setter
@Getter
@Data
public class TransactionModel extends RepresentationModel<TransactionModel> {

    private Long id;

    private Double amount;

    private TypeOfTransaction type;

    private String description;

//    private Long accountId;

}
