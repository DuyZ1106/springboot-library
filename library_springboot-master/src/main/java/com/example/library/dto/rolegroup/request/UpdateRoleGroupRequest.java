package com.example.library.dto.rolegroup.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleGroupRequest {

    @NotBlank
    private String roleGroupName;

    private String description;
}
