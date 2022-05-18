package com.pegasus.ams.controller;

import com.pegasus.ams.mgmt.dto.CustomPageDto;
import com.pegasus.ams.mgmt.dto.request.RoleDTO;
import com.pegasus.ams.mgmt.dto.response.PermissionDTO;
import com.pegasus.ams.mgmt.dto.response.RoleResponseDTO;
import com.pegasus.ams.mgmt.entity.Role;
import com.pegasus.ams.mgmt.mapper.PermissionMapper;
import com.pegasus.ams.mgmt.service.RoleService;
import com.pegasus.base.utils.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PreAuthorize("hasPermission('Role', 'View')")
    @GetMapping(value = "/list")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'View')")
    @GetMapping(value = "/paging")
    public ResponseEntity<CustomPageDto<RoleResponseDTO>> searchRoles(@RequestParam(value = "name", required = false) String name,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                      @RequestParam(value = "sortby", defaultValue = "id,desc") String sortBy) {
        Sort sort = SortUtils.getSortForPaging(sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new ResponseEntity<>(new CustomPageDto<>(roleService.searchRoles(name, pageable), sortBy), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'Create')")
    @PostMapping(value = "")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.createRole(roleDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'Update')")
    @PutMapping(value = "")
    public ResponseEntity<RoleResponseDTO> updateRole(@RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.updateRole(roleDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'View')")
    @GetMapping(value = "/role")
    public ResponseEntity<RoleResponseDTO> getRoleDetail(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(roleService.getRoleDetail(id), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'Delete')")
    @DeleteMapping(value = "/role")
    public ResponseEntity<Void> deleteRole(@RequestParam(value = "ids") List<Long> ids) {
        roleService.deleteRoles(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Role', 'View')")
    @GetMapping(value = "/permissions")
    public ResponseEntity<List<PermissionDTO>> getPermissions() {
        return ResponseEntity.ok(PermissionMapper.toPermissionDtoList(roleService.getPermissions()));
    }

}
