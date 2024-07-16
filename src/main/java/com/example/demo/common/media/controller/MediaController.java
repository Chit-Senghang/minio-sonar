package com.example.demo.common.media.controller;

import com.example.demo.common.media.service.MediaService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@RequestMapping("media")
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("upload")
    public String uploadToBucket(@RequestParam("bucketName") String bucketName,
                                 @RequestParam("file") MultipartFile file) {
        try {
            mediaService.uploadFile(bucketName, file);
            return "File uploaded successfully to bucket: " + bucketName;
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @GetMapping
    public ResponseEntity<Resource> download(@RequestParam("bucketName") String bucketName,
                                             @RequestParam("fileName") String fileName) {
        try {
            InputStream inputStream = mediaService.downloadFile(bucketName, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
