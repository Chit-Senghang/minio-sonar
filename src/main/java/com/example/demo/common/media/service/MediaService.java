package com.example.demo.common.media.service;

import com.example.demo.common.media.MinioClientService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MinioClientService minioClientService;
    
    public void uploadFile(String bucketName, MultipartFile file)
            throws MinioException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        final String bucket = minioClientService.createBucket(bucketName);
        minioClientService.uploadFileToBucket(bucket, file);
    }

    public InputStream downloadFile(String bucketName, String filename)
            throws MinioException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        return minioClientService.downloadFile(bucketName, filename);
    }
}
