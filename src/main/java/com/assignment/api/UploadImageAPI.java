package com.assignment.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.service.ImageUploadService;

import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/api/upload/image")
public class UploadImageAPI {
    
    @Autowired
    private ServletContext context;


    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/avatar")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        int maxFileSize = 1024 * 1024 * 2; // 2MB
        if (file.isEmpty()) {
            response.put("message", "Vui lòng chọn file trước khi upload");
            return ResponseEntity.badRequest().body(response);
        }
        // check file size
        if (file.getSize() > maxFileSize) {
            response.put("message", "File quá lớn, vui lòng chọn file dưới 2MB");
            return ResponseEntity.badRequest().body(response);
        }
        // save file
        try {
            String path = context.getRealPath("resources/images/avatar/");
            String fileName = imageUploadService.storeImage(file, path);
            response.put("message", "Upload file thành công");
            response.put("fileName", fileName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Lỗi upload file");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/product")
    @ResponseBody
    public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        int maxFileSize = 1024 * 1024 * 2; // 2MB
        if (file.isEmpty()) {
            response.put("message", "Vui lòng chọn file trước khi upload");
            return ResponseEntity.badRequest().body(response);
        }
        // check file size
        if (file.getSize() > maxFileSize) {
            response.put("message", "File quá lớn, vui lòng chọn file dưới 2MB");
            return ResponseEntity.badRequest().body(response);
        }
        // save file
        try {
            String path = context.getRealPath("resources/images/products/");
            String fileName = imageUploadService.storeImage(file, path);
            response.put("message", "Upload file thành công");
            response.put("fileName", fileName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Lỗi upload file" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
