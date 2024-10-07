package com.assignment.models.dto.setting;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TOTPEnableDTO {
    private String secretKey; // base32 encoded
    private String QRCode; // base64 encoded
    private String totpCode; // 6 digits
    private String password;

}
