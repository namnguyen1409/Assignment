package com.assignment.models.dto.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationDTO {
    private Long provinceID;
    private Long districtID;
    private Long wardID;
    private String detailAddress;
}
