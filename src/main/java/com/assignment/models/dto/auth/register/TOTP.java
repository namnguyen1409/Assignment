package com.assignment.models.dto.auth.register;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TOTP {

    private String secretKey; // base32 encoded
    private String QRCode; // base64 encoded
    @Pattern(regexp = "^[0-9]{6}$", message = "Mã xác thực không hợp lệ")
    private String totpCode; // 6 digits

    public TOTP(String secretKey, String qRCode,
            @Pattern(regexp = "^[0-9]{6}$", message = "Mã xác thực không hợp lệ") String totpCode) {
        this.secretKey = secretKey;
        QRCode = qRCode;
        this.totpCode = totpCode;
    }
}
