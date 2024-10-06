package com.assignment.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDTO {

    private String code;
    private String name;
    private String description;
    private String image;
    private boolean isActive;
    private String disabledReason;
    
}
