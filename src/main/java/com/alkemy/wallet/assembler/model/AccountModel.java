package com.alkemy.wallet.assembler.model;

import com.alkemy.wallet.model.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.alkemy.wallet.model.enums.TypeOfTransaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Hidden
@Setter
@Getter
@Data
public class AccountModel extends RepresentationModel<AccountModel>  {

    private Long id;

    private Currency currency;

    private Double transactionLimit;

    private Double balance;

    private boolean softDelete;

    private Long user_id;


}
