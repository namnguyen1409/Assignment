package com.assignment.models.dto.setting;

import com.assignment.validation.annotation.FieldsEqual;
import com.assignment.validation.annotation.PasswordStrength;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldsEqual(field1 = "newPassword", field2 = "reNewPassword", message = "Mật khẩu không khớp")
public class ChangePassDTO {
    @NotBlank(message = "Không được để trống")
    private String oldPassword;

    @NotBlank(message = "Không được để trống")
    @PasswordStrength(message = "Mật khẩu không đủ mạnh")
    private String newPassword;

    @NotBlank(message = "Không được để trống")
    private String reNewPassword;

    private String TOTP;

    private boolean enableTOTP; // if true, TOTP is required; if false, TOTP is not required
    
}
