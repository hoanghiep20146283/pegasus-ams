package com.pegasus.ams.mgmt.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDTO {
    private Long id;

    private String module;

    private String action;

}
