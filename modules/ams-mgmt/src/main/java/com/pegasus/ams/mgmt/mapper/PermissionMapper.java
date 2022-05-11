package com.pegasus.ams.mgmt.mapper;

import com.pegasus.ams.mgmt.dto.response.PermissionDTO;
import com.pegasus.ams.mgmt.entity.Permission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermissionMapper {
    private PermissionMapper() {
    }

    public static PermissionDTO toPermissionDto(Permission permission) {
        return PermissionDTO.builder().id(permission.getId())
                .action(permission.getAction())
                .module(permission.getModule())
                .build();
    }


    public static List<PermissionDTO> toPermissionDtoList(List<Permission> permissions) {
        List<PermissionDTO> permissionDtoList = new ArrayList<>();
        permissions.stream().forEach((permission) -> {
            permissionDtoList.add(toPermissionDto(permission));
        });

        return permissionDtoList;
    }

    public static Permission toPermission(PermissionDTO permissionDto) {
        return Permission.builder()
                .action(permissionDto.getAction())
                .module(permissionDto.getModule())
                .build();
    }

    public static Set<PermissionDTO> toPermissionDtoSet(Set<Permission> permissionSet) {
        Set<PermissionDTO> permissonDtoSet = new HashSet<>();
        permissionSet.stream().forEach((permission) -> {
            permissonDtoSet.add(toPermissionDto(permission));
        });

        return permissonDtoSet;
    }
}
