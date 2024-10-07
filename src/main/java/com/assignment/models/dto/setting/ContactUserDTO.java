package com.assignment.models.dto.setting;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUserDTO {
    private String email;
    private String phone;
    private LocalDateTime emailVerifiedAt;
}
