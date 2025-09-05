package com.example.eduplatform.service;

import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.exception.VideoUploadException;
import com.example.eduplatform.props.MinioProperties;
import com.example.eduplatform.service.impl.VideoServiceImpl;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private LessonVideo lessonVideo;

    @InjectMocks
    private VideoServiceImpl videoService;

    @BeforeEach
    void setUp() {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setBucket("test-bucket");
        videoService = new VideoServiceImpl(minioClient, minioProperties);
    }

    @Test
    void shouldUploadVideoSuccessfully() throws Exception {
        when(lessonVideo.getFile()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("video.mp4");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("data".getBytes()));
        when(multipartFile.getSize()).thenReturn(4L);
        when(minioClient.bucketExists(any())).thenReturn(true);

        String result = videoService.upload(lessonVideo);

        assertNotNull(result);
        assertTrue(result.contains(".mp4"));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    void shouldThrowExceptionWhenFileIsEmpty() {
        when(lessonVideo.getFile()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(true);

        VideoUploadException exception =
                assertThrows(VideoUploadException.class, () -> videoService.upload(lessonVideo));

        assertEquals("Video must have a name", exception.getMessage());
        verifyNoInteractions(minioClient);
    }

    @Test
    void shouldThrowExceptionWhenFileHasNoExtension() {
        when(lessonVideo.getFile()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("filewithoutdot");

        assertThrows(VideoUploadException.class, () -> videoService.upload(lessonVideo));
    }

    @Test
    void shouldThrowExceptionWhenMinioFails() throws Exception {
        when(lessonVideo.getFile()).thenReturn(multipartFile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("video.mp4");
        when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());
        when(multipartFile.getSize()).thenReturn(10L);
        when(minioClient.bucketExists(any())).thenReturn(true);
        doThrow(new RuntimeException("MinIO error")).when(minioClient).putObject(any(PutObjectArgs.class));

        VideoUploadException exception =
                assertThrows(VideoUploadException.class, () -> videoService.upload(lessonVideo));

        assertTrue(exception.getMessage().contains("Video upload failed"));
    }
}
