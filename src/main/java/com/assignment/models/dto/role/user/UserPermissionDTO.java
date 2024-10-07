package com.assignment.models.dto.role.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionDTO {
    private String code;
    private String name;
    private String description;
    private Boolean selfRegister;
    private String image;
    private Boolean isActive;
    private Boolean hasPermission;
    private String disabledReason;

    
}
