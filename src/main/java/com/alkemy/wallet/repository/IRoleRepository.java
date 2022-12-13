package com.alkemy.wallet.repository;


import com.alkemy.wallet.listing.RoleName;
import com.alkemy.wallet.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, String> {

    Boolean existsByName(RoleName roleName);

    Role findByName(RoleName role_user);
}
