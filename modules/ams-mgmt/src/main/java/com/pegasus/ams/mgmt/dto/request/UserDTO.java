package com.pegasus.ams.mgmt.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pegasus.ams.mgmt.constant.ProjectConstants;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank
    @Pattern(regexp = ProjectConstants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String username;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean activated;

    private List<Long> roleIds;

}
