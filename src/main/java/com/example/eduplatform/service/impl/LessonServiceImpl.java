package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.entity.Lesson;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.LessonMapper;
import com.example.eduplatform.repository.LessonRepository;
import com.example.eduplatform.service.LessonService;
import com.example.eduplatform.service.ModuleService;
import com.example.eduplatform.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final VideoService videoService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "LessonService::getLessonById", key = "#id")
    public LessonResponse getById(Long id) {
        Lesson lesson = getLessonById(id);
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
    @CachePut(value = "LessonService::getLessonById", key = "#result.id")
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
    @CachePut(value = "LessonService::getLessonById", key = "#result.id")
    public LessonResponse updateLesson(Long id, LessonUpdateRequest lessonUpdateRequest) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id " + id + " not found"));
        lessonMapper.updateEntityFromDto(lessonUpdateRequest, lesson);
        lesson.setUpdatedAt(LocalDateTime.now());
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    @CacheEvict(value = "LessonService::getLessonById", key = "#id")
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson with id " + id + " not found");
        }
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "LessonService::getLessonById", key = "#lessonId")
    public void uploadVideo(Long lessonId, LessonVideo video) {
        Lesson lesson = getLessonById(lessonId);
        String filename = videoService.upload(video);
        lesson.setVideoUrl(filename);
        lessonRepository.save(lesson);
    }

}
