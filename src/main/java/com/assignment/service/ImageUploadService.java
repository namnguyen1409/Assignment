package com.assignment.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    @Autowired 
    private PartialFileHash partialFileHash;
 

    public String storeImage(MultipartFile image, String path) {
        if (image == null) {
            throw new IllegalArgumentException("Hình ảnh không hợp lệ");
        }
        var filename = image.getOriginalFilename();
        if (!hasValidImageExtension(filename)) {
            throw new IllegalArgumentException("Hình ảnh không hợp lệ");
        }
        try {
            if (!isImageFile(image)) {
                throw new IllegalArgumentException("Hình ảnh không hợp lệ");
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Lỗi xác định hình ảnh");
        }
        try {
            String hash = partialFileHash.calculateFileHash(image);
            String extension = FilenameUtils.getExtension(filename);
            String newFilename = hash + "." + extension;
            File newFile = new File(path, newFilename);
            // check is exist
            if (newFile.exists()) {
                return newFilename;
            }
            image.transferTo(newFile);
            return newFilename;
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Lỗi lưu hình ảnh");
        }

    }


    public boolean hasValidImageExtension(String filename) {
        String[] validExtensions = { "jpg", "jpeg", "png", "gif", "bmp", "webp"};
        String fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        return Arrays.asList(validExtensions).contains(fileExtension);
    }

    public boolean isImageFile(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.startsWith("image/");
    }

    
}
