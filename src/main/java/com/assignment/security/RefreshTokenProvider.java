package com.assignment.security;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.config.PropertiesConfig;

@Component
public class RefreshTokenProvider {

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private EncryptionUtil encryptionUtil;

    private int REFRESH_TOKEN_EXPIRATION;

    @PostConstruct
    public void init() {
        this.REFRESH_TOKEN_EXPIRATION = prop.REFRESH_TOKEN_EXPIRATION;
    }

    public String generateRefreshToken(String key) {
        // return string with key and expiration time from now
        String data = key + ":" + Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION).toEpochMilli();
        try {
            return encryptionUtil.encrypt(data);
        } catch (Exception e) {
            return null;
        }
    }

    public String getKeyFromRefreshToken(String refreshToken) {
        try {
            String data = encryptionUtil.decrypt(refreshToken);
            return data.split(":")[0];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            String data = encryptionUtil.decrypt(refreshToken);
            long expiration = Long.parseLong(data.split(":")[1]);
            return Instant.now().toEpochMilli() < expiration;
        } catch (Exception e) {
            return false;
        }
    }

    
}
