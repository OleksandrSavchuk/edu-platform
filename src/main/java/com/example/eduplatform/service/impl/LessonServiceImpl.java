package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.entity.Lesson;
import com.example.eduplatform.mapper.LessonMapper;
import com.example.eduplatform.repository.LessonRepository;
import com.example.eduplatform.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public LessonResponse getById(Long id) {
        return null;
    }

    @Override
    public Lesson getLessonById(Long id) {
        return null;
    }

    @Override
    public List<LessonResponse> getAllLessons(Long moduleId) {
        return List.of();
    }

    @Override
    public LessonResponse createLesson(Long moduleId, LessonCreateRequest lessonCreateRequest) {
        return null;
    }

    @Override
    public LessonResponse updateLesson(Long id, LessonUpdateRequest lessonUpdateRequest) {
        return null;
    }

    @Override
    public void deleteLesson(Long id) {

    }

}
