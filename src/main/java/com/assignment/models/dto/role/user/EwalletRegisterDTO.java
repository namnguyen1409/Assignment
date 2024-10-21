package com.assignment.models.dto.role.user;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EwalletRegisterDTO {
    @AssertTrue(message = "Bạn phải chấp nhận điều khoản để sử dụng dịch vụ")
    private Boolean accept = false;

    @Size(min = 6, max = 6, message = "Mã pin phải có 6 ký tự")
    @Pattern(regexp = "^[0-9]*$", message = "Mã pin chỉ chứa ký tự số")
    private String pin;

    @Size(min = 6, max = 6, message = "Mã pin xác nhận phải có 6 ký tự")
    @Pattern(regexp = "^[0-9]*$", message = "Mã pin xác nhận chỉ chứa ký tự số")
    private String confirmPin;
}
