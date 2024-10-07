package com.assignment.models.dto.role.user;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForceAcceptDTO {

    @AssertTrue(message = "Bạn phải chấp nhận điều khoản để sử dụng dịch vụ")
    private Boolean accept = false;

    
}
