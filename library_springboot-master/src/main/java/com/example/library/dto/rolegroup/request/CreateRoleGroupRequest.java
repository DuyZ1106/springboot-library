package com.example.library.dto.rolegroup.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleGroupRequest {

    @NotBlank
    private String roleGroupCode;

    @NotBlank
    private String roleGroupName;

    private String description;
}
