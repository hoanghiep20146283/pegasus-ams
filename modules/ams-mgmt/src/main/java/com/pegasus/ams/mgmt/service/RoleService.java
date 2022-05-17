package com.pegasus.ams.mgmt.service;

import com.pegasus.ams.mgmt.dto.request.RoleDTO;
import com.pegasus.ams.mgmt.dto.response.RoleResponseDTO;
import com.pegasus.ams.mgmt.entity.Permission;
import com.pegasus.ams.mgmt.entity.Role;
import com.pegasus.ams.mgmt.exception.error.BadRequestException;
import com.pegasus.ams.mgmt.exception.error.NotFoundException;
import com.pegasus.ams.mgmt.mapper.RoleMapper;
import com.pegasus.ams.mgmt.repository.PermissionRepository;
import com.pegasus.ams.mgmt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Page<RoleResponseDTO> searchRoles(String searchValue, Pageable pageable) {
        Page<Role> roles = roleRepository.searchRoles(searchValue, pageable);
        return roles.map(r -> RoleMapper.toRoleResponseDTO(r));
    }

    public RoleResponseDTO createRole(RoleDTO roleDTO) {
        if (roleRepository.findFirstByCode(roleDTO.getCode().toLowerCase()).isPresent()) {
            throw new BadRequestException("Current Code has been use");
        } else if (roleRepository.findFirstByName(roleDTO.getName().toLowerCase()).isPresent()) {
            throw new BadRequestException("Current Name has been use");
        } else {
            Role role = new Role();
            role.setCode(roleDTO.getCode());
            role.setName(roleDTO.getName());
            if (!CollectionUtils.isEmpty(roleDTO.getPermissionIds())) {
                role.setPermissions(permissionRepository.findPermissionsByIds(roleDTO.getPermissionIds()));
            }
            role = roleRepository.save(role);
            return RoleMapper.toRoleResponseDTO(role);
        }
    }

    public RoleResponseDTO updateRole(RoleDTO roleDTO) {
        Optional<Role> roleOptional = roleRepository.findById(roleDTO.getId());
        if (!roleOptional.isPresent()) {
            throw new NotFoundException("Role not found");
        }
        if (roleRepository.findExistByName(roleDTO.getCode()).isPresent()) {
            throw new BadRequestException("Current Code has been use");
        } else if (roleRepository.findExistByCode(roleDTO.getName()).isPresent()) {
            throw new BadRequestException("Current Name has been use");
        } else {
            Role currentRole = roleOptional.get();
            currentRole.setName(roleDTO.getName());
            currentRole.setCode(roleDTO.getCode());
            if (!CollectionUtils.isEmpty(roleDTO.getPermissionIds())) {
                currentRole.setPermissions(permissionRepository.findPermissionsByIds(roleDTO.getPermissionIds()));
            }
            currentRole = roleRepository.save(currentRole);
            return RoleMapper.toRoleResponseDTO(currentRole);
        }
    }

    public RoleResponseDTO getRoleDetail(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        return RoleMapper.toRoleResponseDTO(role);
    }

    public void deleteRoles(List<Long> ids) {
        ids.forEach(id -> {
            Optional<Role> role = roleRepository.findById(id);
            if (!role.isPresent()) {
                throw new NotFoundException("Role not found");
            }
            roleRepository.deleteById(id);
        });
    }

    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }
}
