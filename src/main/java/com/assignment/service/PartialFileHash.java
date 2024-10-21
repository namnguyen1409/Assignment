package com.assignment.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PartialFileHash {

    public String calculateFileHash(MultipartFile file) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var is = file.getInputStream()) {
            byte[] buffer = new byte[8192]; // set buffer size to 8KB
            int read; // read length
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
        } catch (Exception e) {
            return null;
        }
        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
}
