package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.enrollment.EnrollmentCreateRequest;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.mapper.EnrollmentMapper;
import com.example.eduplatform.repository.EnrollmentRepository;
import com.example.eduplatform.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponse getById(Long id) {
        return null;
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentCreateRequest createRequest) {
        return null;
    }

    @Override
    public void deleteEnrollment(Long id) {

    }

}
