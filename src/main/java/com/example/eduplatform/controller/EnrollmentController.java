package com.example.eduplatform.controller;

import com.example.eduplatform.dto.enrollment.EnrollmentCreateRequest;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollments/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Long id) {
        EnrollmentResponse enrollmentResponse = enrollmentService.getById(id);
        return ResponseEntity.ok(enrollmentResponse);
    }

    @GetMapping("/users/{userId}/enrollments")
    public ResponseEntity<List<EnrollmentResponse>> getEnrollmentsByUserId(@PathVariable Long userId) {
        List<EnrollmentResponse> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<EnrollmentResponse> createEnrollment(
            @Valid @RequestBody EnrollmentCreateRequest enrollmentCreateRequest
    ) {
        EnrollmentResponse enrollmentResponse = enrollmentService.createEnrollment(enrollmentCreateRequest);
        return ResponseEntity.ok(enrollmentResponse);
    }

    @DeleteMapping("/enrollments/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

}
