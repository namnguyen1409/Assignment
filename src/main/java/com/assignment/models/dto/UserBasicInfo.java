package com.assignment.models.dto;

import com.assignment.validation.annotation.FieldsEqual;
import com.assignment.validation.annotation.PasswordStrength;
import com.assignment.validation.annotation.UsernameUnique;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldsEqual(field1 = "password", field2 = "rePassword", message = "Mật khẩu không trùng khớp")
public class UserBasicInfo {

    @UsernameUnique(message = "Tên đăng nhập đã tồn tại")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 6, max = 50, message = "Tên đăng nhập phải từ 6 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @PasswordStrength(message = "Mật khẩu không đủ mạnh")
    private String password;

    private String rePassword;

    public UserBasicInfo() {
    }

    public UserBasicInfo(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

}
