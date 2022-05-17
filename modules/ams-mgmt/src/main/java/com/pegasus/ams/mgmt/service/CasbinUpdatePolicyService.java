package com.pegasus.ams.mgmt.service;

import com.pegasus.ams.mgmt.entity.Permission;
import com.pegasus.ams.mgmt.entity.Role;
import com.pegasus.ams.mgmt.repository.PermissionRepository;
import com.pegasus.ams.mgmt.repository.RoleRepository;
import com.pegasus.ams.mgmt.repository.UserRepository;
import com.pegasus.base.service.CasbinEnforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasbinUpdatePolicyService {

    @Autowired
    CasbinEnforcer casbinEnforcer;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public void updatePolicy(Long userId, boolean firstTime) {
        if (!casbinEnforcer.getEnforcer().getRolesForUser(userId.toString()).isEmpty() && firstTime) {
            return;
        }

        roleRepository.findAllRolesByUserId(userId).forEach(r -> {
            if (firstTime) {
                updateRolePolicy(r);
            }
            casbinEnforcer.getEnforcer().addGroupingPolicy(userId.toString(), r.getId().toString());
        });
    }

    public void updateRolePolicy(Role role) {
        casbinEnforcer.getEnforcer().getPermissionsForUser(role.getId().toString()).forEach(p ->
                casbinEnforcer.getEnforcer().removePolicy(p));
        String roleId = role.getId().toString();
        List<Permission> permissions = role.getPermissions().stream().collect(Collectors.toList());
        permissions.forEach(p ->
                casbinEnforcer.getEnforcer().addPolicy(roleId,
                        p.getModule().toUpperCase(),
                        p.getAction().toUpperCase()));
    }
}
