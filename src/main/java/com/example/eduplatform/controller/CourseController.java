package com.example.eduplatform.controller;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(
        name = "Course Controller",
        description = "Course API"
)
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get course by ID",
            description = "Returns detailed information about a specific course by its ID."
    )
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    @Operation(
            summary = "Get all courses",
            description = "Returns a list of all available courses."
    )
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @Operation(
            summary = "Create a new course",
            description = "Creates a new course. Only instructors are allowed to create the course."
    )
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        CourseResponse courseResponse = courseService.createCourse(courseCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#id)")
    @Operation(
            summary = "Update a course",
            description = "Updates an existing course. " +
                    "Only the course owner can update the ."
    )
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateRequest courseUpdateRequest) {
        CourseResponse courseResponse = courseService.updateCourse(id, courseUpdateRequest);
        return ResponseEntity.ok(courseResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#id)")
    @Operation(
            summary = "Delete a course",
            description = "Deletes a course by its ID. Only the course owner can delete the course."
    )
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
