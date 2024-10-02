package com.assignment.models.entities.user;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "registration_id")
    private String registrationId;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "platform")
    private String platform;

    @Column(name = "browser")
    private String browser;

    @Column(name = "canvas_fingerprint")
    private String canvasFingerprint;

    @OneToMany(mappedBy = "device")
    private List<UserDevice> userDevices = new ArrayList<>();

    public Device() {
        this.registrationId = UUID.randomUUID().toString();
    }

    public boolean isSameDevice(Device device) {
        return this.browser.equals(device.browser)
        && this.canvasFingerprint.equals(device.canvasFingerprint)
        && this.deviceName.equals(device.deviceName)
        && this.platform.equals(device.platform)
        && this.screenSize.equals(device.screenSize);
    }



    // custom equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Device device = (Device) obj;
        return registrationId.equals(device.registrationId)
        && deviceName.equals(device.deviceName)
        && platform.equals(device.platform)
        && browser.equals(device.browser)
        && canvasFingerprint.equals(device.canvasFingerprint);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(registrationId, deviceName, platform, browser, canvasFingerprint);
    }
}
