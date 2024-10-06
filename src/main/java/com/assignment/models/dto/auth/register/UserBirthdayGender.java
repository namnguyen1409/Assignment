package com.assignment.models.dto.auth.register;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.assignment.validation.annotation.AgeCheck;
import com.assignment.validation.annotation.OtherGender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@OtherGender(gender="gender", otherGender="otherGender", message = "Giới tính không hợp lệ")
public class UserBirthdayGender {
    // age >= 12
    @Past(message = "Ngày sinh phải trước ngày hiện tại")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Ngày sinh không được để trống") // Change to @NotNull
    @AgeCheck(min =  12, max= 125 , message = "Tuổi phải từ 12 đến 125") // Add @AgeCheck
    private LocalDate birthday;

    @NotBlank(message = "Giới tính không được để trống")
    @Pattern(regexp = "^(Nam|Nữ|Khác|Không muốn trả lời)$", message = "Giới tính không hợp lệ")
    private String gender;

    private String otherGender;
}
