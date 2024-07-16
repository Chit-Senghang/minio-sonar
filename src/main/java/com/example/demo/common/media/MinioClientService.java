package com.example.demo.common.media;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioClientService {
    private MinioClient connectToS3() {
        try {
            return MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("muHSScTot4kgqUaZORsS", "ccCEFVOZXrdsizhA8DFbbkqO8Or8ao6GbgcX4kQg")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MinIO server", e);
        }
    }

    public void uploadFileToBucket(String bucketName, MultipartFile file)
            throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        final MinioClient minioClient = connectToS3();
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(file.getOriginalFilename())
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(file.getContentType())
                        .build());
    }

    public String createBucket(final String bucketName)
            throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        final MinioClient minioClient = connectToS3();
        final boolean bucketIsFound = minioClient.bucketExists(BucketExistsArgs
                .builder()
                .bucket(bucketName)
                .build());
        if (!bucketIsFound) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return bucketName;
    }

    public InputStream downloadFile(String bucketName, String fileName)
            throws MinioException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        final MinioClient minioClient = connectToS3();
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }
}
