package com.example.eduplatform.controller;

import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<CourseResponse>> getAllMyEnrollments() {
        List<CourseResponse> courses = enrollmentService.getEnrollmentsForCurrentUser();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{courseId}/users")
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#courseId)")
    public ResponseEntity<List<UserResponse>> getEnrolledUsers(@PathVariable Long courseId) {
        List<UserResponse> users = enrollmentService.getUsersByCourse(courseId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/courses/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentResponse> createEnrollment(@PathVariable Long courseId) {
        EnrollmentResponse enrollmentResponse = enrollmentService.createEnrollment(courseId);
        return ResponseEntity.ok(enrollmentResponse);
    }

    @DeleteMapping("/courses/{courseId}")
    @PreAuthorize("@customSecurityExpression.isEnrolled(#courseId)")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long courseId) {
        enrollmentService.deleteEnrollment(courseId);
        return ResponseEntity.noContent().build();
    }

}
