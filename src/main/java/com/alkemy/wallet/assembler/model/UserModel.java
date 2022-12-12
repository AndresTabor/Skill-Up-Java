package com.alkemy.wallet.assembler.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Data
public class UserModel extends RepresentationModel<TransactionModel> {

    private String firstName;

    private String lastName;

    private String email;
}
