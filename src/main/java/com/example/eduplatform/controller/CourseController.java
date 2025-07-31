package com.example.eduplatform.controller;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.service.CourseService;
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
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        CourseResponse courseResponse = courseService.createCourse(courseCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') and @customSecurityExpression.isCourseOwner(#id)")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateRequest courseUpdateRequest) {
        CourseResponse courseResponse = courseService.updateCourse(id, courseUpdateRequest);
        return ResponseEntity.ok(courseResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') and @customSecurityExpression.isCourseOwner(#id)")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
