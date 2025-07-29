package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.enrollment.EnrollmentCreateRequest;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.Enrollment;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.EnrollmentMapper;
import com.example.eduplatform.repository.EnrollmentRepository;
import com.example.eduplatform.service.CourseService;
import com.example.eduplatform.service.EnrollmentService;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final UserService userService;
    private final CourseService courseService;

    @Override
    public EnrollmentResponse getById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment with id " + id + " not found"));
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByUserId(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByUserId(userId);
        return enrollmentMapper.toDto(enrollments);
    }

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentCreateRequest createRequest) {
        User user = userService.getUserById(createRequest.getUserId());
        Course course = courseService.getCourseById(createRequest.getCourseId());
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentMapper.toDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment with id " + id + " not found");
        }
        enrollmentRepository.deleteById(id);
    }

}
