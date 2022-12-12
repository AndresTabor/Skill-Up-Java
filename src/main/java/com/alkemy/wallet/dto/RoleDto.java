package com.alkemy.wallet.dto;

import com.alkemy.wallet.listing.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;

    @NotNull(message = "{rolename.notnull}")
    private RoleName name;

    private String description;

    private Date creationDate;

    private Date updateDate;
}
