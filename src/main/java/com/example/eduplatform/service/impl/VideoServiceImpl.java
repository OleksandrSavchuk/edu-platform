package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.props.MinioProperties;
import com.example.eduplatform.service.VideoService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(LessonVideo video) {
        return "";
    }

}
