package com.alkemy.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {

    @NotEmpty(message = "{firstname.notnull}")
    private String firstName;

    @NotEmpty(message = "{lastname.notnull}")
    private String lastName;

    @NotEmpty
    @Email(message = "{email.notnull}")
    private String email;

    private String token;

}
