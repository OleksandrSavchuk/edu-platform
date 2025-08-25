package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EnrollmentResolver {

    private final EnrollmentService enrollmentService;

    @QueryMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<EnrollmentResponse> getAllMyEnrollments() {
        return enrollmentService.getEnrollmentsForCurrentUser();
    }

    @QueryMapping
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#courseId)")
    public List<UserResponse> getEnrolledUsers(@Argument Long courseId) {
        return enrollmentService.getUsersByCourse(courseId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('STUDENT')")
    public EnrollmentResponse createEnrollment(@Argument Long courseId) {
        return enrollmentService.createEnrollment(courseId);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isEnrolled(#courseId)")
    public Boolean deleteEnrollment(@Argument Long courseId) {
        enrollmentService.deleteEnrollment(courseId);
        return true;
    }


}
