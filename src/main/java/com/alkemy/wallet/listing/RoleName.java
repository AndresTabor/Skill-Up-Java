package com.alkemy.wallet.listing;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;


@Hidden
@Getter
public enum RoleName {
    ROLE_ADMIN("Administrator"),
    ROLE_USER("User");

    private final String name;

    private RoleName(String name) {
        this.name = name;
    }

}
