package com.assignment.models.dto.setting;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private String avatar;
}
