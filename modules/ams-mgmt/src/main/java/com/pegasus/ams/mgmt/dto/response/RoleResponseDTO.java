package com.pegasus.ams.mgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Set<PermissionDTO> permissions;
}
