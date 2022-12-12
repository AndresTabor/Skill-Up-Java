package com.alkemy.wallet.assembler.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Hidden
@Setter
@Getter
public class UserModel extends RepresentationModel<TransactionModel> {

    private String firstName;

    private String lastName;

    private String email;
}
