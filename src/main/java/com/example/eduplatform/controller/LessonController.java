package com.example.eduplatform.controller;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessLesson(@id)")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        LessonResponse lessonResponse = lessonService.getById(id);
        return ResponseEntity.ok(lessonResponse);
    }

    @GetMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("@customSecurityExpression.canAccessModule(@moduleId)")
    public ResponseEntity<List<LessonResponse>> getAllLessons(@PathVariable Long moduleId) {
        List<LessonResponse> lessons = lessonService.getAllLessons(moduleId);
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#moduleId)")
    public ResponseEntity<LessonResponse> createLesson(@PathVariable Long moduleId,
                                                       @Valid @RequestBody LessonCreateRequest lessonCreateRequest) {
        LessonResponse lessonResponse = lessonService.createLesson(moduleId, lessonCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonResponse);
    }

    @PutMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable Long id,
                                                       @Valid @RequestBody LessonUpdateRequest lessonUpdateRequest) {
        LessonResponse lessonResponse = lessonService.updateLesson(id, lessonUpdateRequest);
        return ResponseEntity.ok(lessonResponse);
    }

    @DeleteMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

}
