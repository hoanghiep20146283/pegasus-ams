package com.pegasus.ams.mgmt.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Set<PermissionDTO> permissions;
}
