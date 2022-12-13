package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.RoleDto;
import com.alkemy.wallet.exception.ResourceFoundException;
import com.alkemy.wallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.listing.RoleName;
import com.alkemy.wallet.mapper.Mapper;
import com.alkemy.wallet.model.Role;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.service.interfaces.IRoleService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;
    private final Mapper mapper;
    private final MessageSource messageSource;

    public RoleService(IRoleRepository roleRepository, Mapper mapper, MessageSource messageSource) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }


    @Override
    public Role findByName(RoleName roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role != null ) {
            return role;
        }
        throw new ResourceNotFoundException(messageSource.getMessage("rolename.notfound.exception",
                new Object[]{roleName}, Locale.ENGLISH));
    }

    @Override
    public Role createRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new ResourceFoundException("Role already exists");
        }
        return roleRepository.save(role);
    }

}
