package com.pegasus.ams.mgmt.mapper;

import com.pegasus.ams.mgmt.dto.response.RoleResponseDTO;
import com.pegasus.ams.mgmt.entity.Role;

public class RoleMapper {
    private RoleMapper() {

    }

    public static RoleResponseDTO toRoleResponseDTO(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .code(role.getCode())
                .name(role.getName())
                .build();
    }

    public static Role toRoleDTO(RoleResponseDTO roleResponseDTO) {
        return Role.builder()
                .id(roleResponseDTO.getId())
                .code(roleResponseDTO.getCode())
                .name(roleResponseDTO.getName())
                .build();
    }
}
