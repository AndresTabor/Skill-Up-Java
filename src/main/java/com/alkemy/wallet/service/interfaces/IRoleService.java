package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.RoleDto;
import com.alkemy.wallet.listing.RoleName;
import com.alkemy.wallet.model.Role;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface IRoleService {

    Role findByName(RoleName roleUser);

    Role createRole(Role role);
}
