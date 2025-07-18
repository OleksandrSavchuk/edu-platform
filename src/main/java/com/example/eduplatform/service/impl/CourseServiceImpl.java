package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.repository.CourseRepository;
import com.example.eduplatform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse getCourseById(Long id) {
        return null;
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return List.of();
    }

    @Override
    public CourseResponse createCourse(CourseCreateRequest courseCreateRequest) {
        return null;
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest courseUpdateRequest) {
        return null;
    }

    @Override
    public void deleteCourse(Long id) {

    }

    @Override
    public boolean existsByIdAndInstructorId(Long courseId, Long instructorId) {
        return false;
    }

}
