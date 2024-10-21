package com.assignment.models.dto.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationDTO {
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String detailAddress;
}
