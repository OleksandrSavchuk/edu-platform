package com.example.eduplatform.service;

import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentResponse> getEnrollmentsForCurrentUser();

    List<UserResponse> getUsersByCourse(Long courseId);

    EnrollmentResponse createEnrollment(Long courseId);

    void deleteEnrollment(Long courseId);

    boolean existsByCourseIdAndUserId(Long courseId, Long userId);

}
