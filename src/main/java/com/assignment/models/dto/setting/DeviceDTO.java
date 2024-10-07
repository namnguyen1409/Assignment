package com.assignment.models.dto.setting;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private long id;
    private String deviceName;
    private String platform;
    private String browser;
    private LocalDate lastLogin;
    private Boolean isRevoked;
    private Boolean isCurrentDevice;
}
