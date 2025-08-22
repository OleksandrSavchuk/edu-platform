package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.Enrollment;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.AlreadyEnrolledException;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.CourseMapper;
import com.example.eduplatform.mapper.EnrollmentMapper;
import com.example.eduplatform.mapper.UserMapper;
import com.example.eduplatform.repository.EnrollmentRepository;
import com.example.eduplatform.service.CourseService;
import com.example.eduplatform.service.EnrollmentService;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final UserService userService;
    private final CourseService courseService;
    private final UserMapper userMapper;


    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getEnrollmentsForCurrentUser() {
        Long userId = getCurrentUser().getId();
        List<Enrollment> enrollments = enrollmentRepository.findAllByUserId(userId);
        return enrollmentMapper.toDto(enrollments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByCourse(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(courseId);
        return enrollments.stream()
                .map(Enrollment::getUser)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrollmentResponse createEnrollment(Long courseId) {
        User user = getCurrentUser();
        if (enrollmentRepository.existsByCourseIdAndUserId(courseId, user.getId())) {
            throw new AlreadyEnrolledException("User is already enrolled in this course");
        }
        Course course = courseService.getCourseById(courseId);
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentMapper.toDto(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void deleteEnrollment(Long courseId) {
        Long userId = getCurrentUser().getId();
        if (!enrollmentRepository.existsByCourseIdAndUserId(courseId, userId)) {
            throw new ResourceNotFoundException("Enrollment not found");
        }
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndUserId(courseId, userId);
        enrollmentRepository.delete(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCourseIdAndUserId(Long courseId, Long userId) {
        return enrollmentRepository.existsByCourseIdAndUserId(courseId, userId);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

}
