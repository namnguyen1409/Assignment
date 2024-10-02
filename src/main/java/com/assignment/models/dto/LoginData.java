package com.assignment.models.dto;

import com.assignment.validation.annotation.UserInfoExist;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginData {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @UserInfoExist(message = "Thông tin đăng nhập không tồn tại")
    private String loginInfo;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private boolean rememberMe;

}
