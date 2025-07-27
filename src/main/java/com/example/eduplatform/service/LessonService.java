package com.example.eduplatform.service;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.entity.Lesson;

import java.util.List;

public interface LessonService {

    LessonResponse getById(Long id);

    Lesson getLessonById(Long id);

    List<LessonResponse> getAllLessons(Long moduleId);

    LessonResponse createLesson(Long moduleId, LessonCreateRequest lessonCreateRequest);

    LessonResponse updateLesson(Long id, LessonUpdateRequest lessonUpdateRequest);

    void deleteLesson(Long id);

}
