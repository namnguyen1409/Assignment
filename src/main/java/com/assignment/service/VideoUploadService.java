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
public class VideoUploadService {

    @Autowired
    private PartialFileHash partialFileHash;

    public String storeVideo(MultipartFile video, String path) {
        if (video == null) {
            throw new IllegalArgumentException("Video không hợp lệ");
        }
        var filename = video.getOriginalFilename();
        if (!hasValidVideoExtension(filename)) {
            throw new IllegalArgumentException("Video không hợp lệ");
        }
        try {
            if (!isVideoFile(video)) {
                throw new IllegalArgumentException("Video không hợp lệ");
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Lỗi xác định video");
        }
        try {
            String hash = partialFileHash.calculateFileHash(video);
            String extension = FilenameUtils.getExtension(filename);
            String newFilename = hash + "." + extension;
            File newFile = new File(path, newFilename);
            // check is exist
            if (newFile.exists()) {
                return newFilename;
            }
            video.transferTo(newFile);
            return newFilename;
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    public boolean hasValidVideoExtension(String filename) {
        String[] validExtensions = { "mp4", "avi", "flv", "wmv", "mov", "webm" };
        String fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        return Arrays.asList(validExtensions).contains(fileExtension);
    }

    public boolean isVideoFile(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.startsWith("video");
    }
    
}
