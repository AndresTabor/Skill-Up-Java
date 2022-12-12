package com.alkemy.wallet.util;

import com.alkemy.wallet.listing.RoleName;
import com.alkemy.wallet.model.Role;
import com.alkemy.wallet.service.interfaces.IRoleService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Hidden
@Component
public class DataLoaderRole implements CommandLineRunner {

    @Autowired
    private IRoleService roleService;

    @Override
    public void run(String... args) throws Exception {

        Role role;
        role = Role.builder()
                .name(RoleName.ROLE_ADMIN).build();
        roleService.createRole(role);

        role = Role.builder()
                .name(RoleName.ROLE_USER).build();
        roleService.createRole(role);

    }
}
