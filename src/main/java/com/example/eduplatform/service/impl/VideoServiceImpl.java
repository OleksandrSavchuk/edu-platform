package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.exception.VideoUploadException;
import com.example.eduplatform.props.MinioProperties;
import com.example.eduplatform.service.VideoService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(LessonVideo video) {
        MultipartFile file = video.getFile();
        validateVideo(file);

        String fileName = generateFileName(file);
        try {
            createBucket();
            saveVideo(file, fileName);
        } catch (Exception e) {
            throw new VideoUploadException("Video upload failed: " + e.getMessage());
        }
        return fileName;
    }

    private void validateVideo(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename().trim().isEmpty()) {
            throw new VideoUploadException("Video must have a name");
        }

    }

    private void createBucket() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "." + getExtension(file);
    }

    private String getExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name.contains(".")) {
            return name.substring(name.lastIndexOf('.') + 1);
        }
        throw new VideoUploadException("Invalid file format");
    }

    private void saveVideo(MultipartFile file, String filename) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, file.getSize(), -1)
                    .bucket(minioProperties.getBucket())
                    .object(filename)
                    .build());
        }
    }
}
