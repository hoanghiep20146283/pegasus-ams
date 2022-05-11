package com.pegasus.ams.mgmt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class UserResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean activated;

    private Set<RoleResponseDTO> roles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
}
