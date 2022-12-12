package com.alkemy.wallet.listing;

import lombok.Getter;

@Getter
public enum RoleName {
    ROLE_ADMIN("Administrator"),
    ROLE_USER("User");

    private final String name;

    private RoleName(String name) {
        this.name = name;
    }

}
