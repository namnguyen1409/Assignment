package com.assignment.models.dto.auth.register;

import com.assignment.validation.annotation.EmailUnique;
import com.assignment.validation.annotation.PhoneUnique;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPhoneEmail {

    @Pattern(regexp = "^(\\+84|84|0)(3|5|7|8|9|1[2|6|8|9])[0-9]{8}$", message = "Số điện thoại không hợp lệ")
    @PhoneUnique(message = "Số điện thoại đã tồn tại!")
    private String phone;

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email không hợp lệ")
    @EmailUnique(message = "Email đã tồn tại!")
    private String email;

}
