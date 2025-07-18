package com.example.eduplatform.service;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;

import java.util.List;

public interface CourseService {

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse createCourse(CourseCreateRequest courseCreateRequest);

    CourseResponse updateCourse(Long id, CourseUpdateRequest courseUpdateRequest);

    void deleteCourse(Long id);

}
