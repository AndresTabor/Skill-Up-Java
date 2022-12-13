package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.Role;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UserDto {

    private Long id;

    @NotEmpty(message = "{firstname.notnull}")
    private String firstName;

    @NotEmpty(message = "{lastname.notnull}")
    private String lastName;

    @Email(message = "{email.notnull}")
    private String email;

    @NotEmpty(message = "{password.notnull}")
    private String password;

    private Role role;

    private Date creationDate;

    private Date updateDate;

}
