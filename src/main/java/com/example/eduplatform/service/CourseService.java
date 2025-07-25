package com.example.eduplatform.service;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.entity.Course;

import java.util.List;

public interface CourseService {

    CourseResponse getById(Long id);

    Course getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse createCourse(CourseCreateRequest courseCreateRequest);

    CourseResponse updateCourse(Long id, CourseUpdateRequest courseUpdateRequest);

    void deleteCourse(Long id);

    boolean existsByIdAndInstructorId(Long courseId, Long instructorId);

}
