package com.assignment.models.dto.role.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellRegisterDTO {
    // tên cửa hàng
    @NotBlank(message = "Tên cửa hàng không được để trống")
    @Size(min = 6, max = 255, message = "Tên cửa hàng phải từ 6 đến 255 ký tự")
    @Pattern(regexp = "^[^!@#$%^&*()_+=\\[\\]{}|,;:'\"<>?/\\\\~`]*$", message = "Tên cửa hàng không được chứa ký tự đặc biệt")
    private String name;

    // mô tả cửa hàng
    @NotBlank(message = "Mô tả cửa hàng không được để trống")
    @Size(min = 6, max = 500, message = "Mô tả cửa hàng phải từ 6 đến 500 ký tự")
    private String description;

    // địa chỉ cửa hàng
    @NotNull(message = "Phải chọn tỉnh/thành phố")
    private Long provinceId;

    @NotNull(message = "Phải chọn quận/huyện")
    private Long districtId;

    @NotNull(message = "Phải chọn phường/xã")
    private Long wardId;

    @NotBlank(message = "Địa chỉ cửa hàng không được để trống")
    @Size(min = 6, max = 255, message = "Địa chỉ cửa hàng phải từ 6 đến 255 ký tự")
    private String detailAddress;

    // nút chấp nhận điều khoản
    @AssertTrue(message = "Bạn phải chấp nhận điều khoản")
    private Boolean acceptData;
}
