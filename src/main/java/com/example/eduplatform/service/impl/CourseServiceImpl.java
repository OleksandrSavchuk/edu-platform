package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.CourseMapper;
import com.example.eduplatform.repository.CourseRepository;
import com.example.eduplatform.service.CourseService;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;

    @Override
    public CourseResponse getById(Long id) {
        User user = getCurrentUser();
        if (user.getRole().name().equals("INSTRUCTOR")) {
            Course course = courseRepository.findByIdAndInstructorId(id, user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
            return courseMapper.toDto(course);
        }
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
        return courseMapper.toDto(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        User user = getCurrentUser();
        if (user.getRole().name().equals("INSTRUCTOR")) {
            return courseMapper.toDto(courseRepository.findAllByInstructorId(user.getId()));
        }
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toDto(courses);
    }

    @Override
    public CourseResponse createCourse(CourseCreateRequest courseCreateRequest) {
        User instructor = getCurrentUser();
        Course course = courseMapper.toEntity(courseCreateRequest);
        course.setInstructor(instructor);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        courseMapper.toDto(courseRepository.save(course));
        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest courseUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
        courseMapper.updateEntityFromDto(courseUpdateRequest, course);
        course.setUpdatedAt(LocalDateTime.now());
        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course with id " + id + " not found");
        }
        courseRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdAndInstructorId(Long courseId, Long instructorId) {
        return courseRepository.existsByIdAndInstructorId(courseId, instructorId);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

}
