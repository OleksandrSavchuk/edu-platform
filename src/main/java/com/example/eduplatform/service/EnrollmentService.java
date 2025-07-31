package com.example.eduplatform.service;

import com.example.eduplatform.dto.enrollment.EnrollmentCreateRequest;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse getById(Long id);

    List<EnrollmentResponse> getEnrollmentsByUserId(Long userId);

    EnrollmentResponse createEnrollment(EnrollmentCreateRequest createRequest);

    void deleteEnrollment(Long id);

    boolean existsByCourseIdAndUserId(Long courseId, Long userId);

}
