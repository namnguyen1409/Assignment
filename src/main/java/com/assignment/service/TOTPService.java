package com.assignment.service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

@Service
public class TOTPService {

    private final TimeBasedOneTimePasswordGenerator totp;
    private final SecureRandom secureRandom;

    public TOTPService() {
        this.totp = new TimeBasedOneTimePasswordGenerator();
        this.secureRandom = new SecureRandom();
    }

    public String generateQRCodeURL(String secretKey, String accountName, String issuer) {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, accountName, secretKey, issuer);
    }

    public String generateSecretKey() {
        Base32 base32 = new Base32();
        byte[] randomBytes = new byte[10];
        secureRandom.nextBytes(randomBytes);
        return base32.encodeAsString(randomBytes);
    }

    private byte[] decodeSecretKey(String secret) {
        Base32 base32 = new Base32();
        return base32.decode(secret);
    }

    public boolean validationTOTP(String secret, String code) {
        try {
            byte[] decodedKey = decodeSecretKey(secret);
            Key key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "RAW");

            Instant now = Instant.now();

            for (int i = -1; i <= 1; i++) {
                Instant adjustedTime = now.plus(i * 30, ChronoUnit.SECONDS);
                int generatedCode = totp.generateOneTimePassword(key, adjustedTime);
                if (Integer.parseInt(code) == generatedCode) {
                    return true;
                }
            }
        } catch (NumberFormatException | InvalidKeyException e) {
            return false;
        }
        return false;
    }

}
