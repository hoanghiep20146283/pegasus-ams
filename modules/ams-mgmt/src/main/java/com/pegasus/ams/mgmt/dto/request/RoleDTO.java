package com.pegasus.ams.mgmt.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String name;

    private String code;

    private List<Long> permissionIds;
}
