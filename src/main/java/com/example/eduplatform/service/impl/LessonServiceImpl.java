package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.entity.Lesson;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.LessonMapper;
import com.example.eduplatform.repository.LessonRepository;
import com.example.eduplatform.service.LessonService;
import com.example.eduplatform.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final ModuleService moduleService;

    @Override
    @Transactional(readOnly = true)
    public LessonResponse getById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id " + id + " not found"));
        return lessonMapper.toDto(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> getAllLessons(Long moduleId) {
        List<Lesson> lessons = lessonRepository.findAllByModuleId(moduleId);
        return lessonMapper.toDto(lessons);
    }

    @Override
    @Transactional
    public LessonResponse createLesson(Long moduleId, LessonCreateRequest lessonCreateRequest) {
        Module module = moduleService.getModuleById(moduleId);
        Lesson lesson = lessonMapper.toEntity(lessonCreateRequest);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setUpdatedAt(LocalDateTime.now());
        lesson.setModule(module);
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public LessonResponse updateLesson(Long id, LessonUpdateRequest lessonUpdateRequest) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id " + id + " not found"));
        lessonMapper.updateEntityFromDto(lessonUpdateRequest, lesson);
        lesson.setUpdatedAt(LocalDateTime.now());
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson with id " + id + " not found");
        }
        lessonRepository.deleteById(id);
    }

}
