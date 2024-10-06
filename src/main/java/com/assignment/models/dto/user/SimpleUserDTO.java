package com.assignment.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleUserDTO {
    private String username;
    private String avatar;
}
