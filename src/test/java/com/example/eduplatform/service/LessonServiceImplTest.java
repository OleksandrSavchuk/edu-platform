package com.example.eduplatform.service;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.entity.Lesson;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.LessonMapper;
import com.example.eduplatform.repository.LessonRepository;
import com.example.eduplatform.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private ModuleService moduleService;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void shouldReturnLessonWhenExists() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        LessonResponse dto = LessonResponse.builder()
                .id(1L)
                .build();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonResponse result = lessonService.getById(1L);

        assertEquals(1L, result.getId());
        verify(lessonRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenLessonNotFound() {
        when(lessonRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lessonService.getById(99L));
    }

    @Test
    void shouldCreateLessonSuccessfully() {
        LessonCreateRequest request = LessonCreateRequest.builder().build();
        Module module = new Module();
        module.setId(1L);

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setModule(module);

        LessonResponse dto = LessonResponse.builder().build();
        dto.setId(1L);

        when(moduleService.getModuleById(1L)).thenReturn(module);
        when(lessonMapper.toEntity(request)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonResponse result = lessonService.createLesson(1L, request);

        assertEquals(1L, result.getId());
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldUpdateLessonSuccessfully() {
        LessonUpdateRequest request = LessonUpdateRequest.builder().build();
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        LessonResponse dto = LessonResponse.builder().build();
        dto.setId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonResponse result = lessonService.updateLesson(1L, request);

        assertEquals(1L, result.getId());
        verify(lessonMapper).updateEntityFromDto(request, lesson);
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingLesson() {
        LessonUpdateRequest request = LessonUpdateRequest.builder().build();
        when(lessonRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lessonService.updateLesson(100L, request));
    }

    @Test
    void shouldDeleteLessonSuccessfully() {
        when(lessonRepository.existsById(1L)).thenReturn(true);

        lessonService.deleteLesson(1L);

        verify(lessonRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingLesson() {
        when(lessonRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> lessonService.deleteLesson(99L));
    }

    @Test
    void shouldUploadVideoSuccessfully() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        MultipartFile multipartFile = mock(MultipartFile.class);
        LessonVideo video = new LessonVideo();
        video.setFile(multipartFile);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(videoService.upload(video)).thenReturn("stored-video.mp4");
        when(lessonRepository.save(lesson)).thenReturn(lesson);

        lessonService.uploadVideo(1L, video);

        assertEquals("stored-video.mp4", lesson.getVideoUrl());
        verify(videoService).upload(video);
        verify(lessonRepository).save(lesson);
    }
}
