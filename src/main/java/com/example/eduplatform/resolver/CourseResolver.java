package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseResolver {

    private final CourseService courseService;

    @QueryMapping
    public CourseResponse getCourseById(@Argument Long id) {
        return courseService.getById(id);
    }

    @QueryMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @MutationMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public CourseResponse createCourse(@Argument CourseCreateRequest courseCreateRequest) {
        return courseService.createCourse(courseCreateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#id)")
    public CourseResponse updateCourse(@Argument Long id, @Argument CourseUpdateRequest courseUpdateRequest) {
        return courseService.updateCourse(id, courseUpdateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#id)")
    public Boolean deleteCourse(@Argument Long id) {
        courseService.deleteCourse(id);
        return true;
    }

}
