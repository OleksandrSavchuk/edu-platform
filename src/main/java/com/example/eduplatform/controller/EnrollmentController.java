package com.example.eduplatform.controller;

import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Tag(
        name = "Enrollment Controller",
        description = "Enrollment API"
)
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Get enrolled courses",
            description = "Returns a list of courses the currently authenticated student is enrolled in."
    )
    public ResponseEntity<List<EnrollmentResponse>> getAllMyEnrollments() {
        List<EnrollmentResponse> courses = enrollmentService.getEnrollmentsForCurrentUser();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{courseId}/users")
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#courseId)")
    @Operation(
            summary = "Get users enrolled in a course",
            description = "Returns a list of users enrolled in the specified course. Accessible only by the course owner."
    )
    public ResponseEntity<List<UserResponse>> getEnrolledUsers(@PathVariable Long courseId) {
        List<UserResponse> users = enrollmentService.getUsersByCourse(courseId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/courses/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Enroll in a course",
            description = "Creates an enrollment for the currently authenticated student in the specified course."
    )
    public ResponseEntity<EnrollmentResponse> createEnrollment(@PathVariable Long courseId) {
        EnrollmentResponse enrollmentResponse = enrollmentService.createEnrollment(courseId);
        return ResponseEntity.ok(enrollmentResponse);
    }

    @DeleteMapping("/courses/{courseId}")
    @PreAuthorize("@customSecurityExpression.isEnrolled(#courseId)")
    @Operation(
            summary = "Unenroll from a course",
            description = "Removes the currently authenticated student's enrollment from the specified course."
    )
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long courseId) {
        enrollmentService.deleteEnrollment(courseId);
        return ResponseEntity.noContent().build();
    }

}
