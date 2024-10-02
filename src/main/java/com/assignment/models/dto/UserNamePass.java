package com.assignment.models.dto;

import com.assignment.validation.annotation.FieldsEqual;
import com.assignment.validation.annotation.PasswordStrength;
import com.assignment.validation.annotation.UsernameUnique;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldsEqual(field1 = "password", field2 = "rePassword", message = "Mật khẩu không trùng khớp")
public class UserNamePass {

    @UsernameUnique(message = "Tên đăng nhập đã tồn tại")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 6, max = 50, message = "Tên đăng nhập phải từ 6 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9.]*$", message = "Tên đăng nhập chỉ chứa chữ cái, số và dấu chấm")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @PasswordStrength(message = "Mật khẩu không đủ mạnh")
    private String password;

    private String rePassword;

}
