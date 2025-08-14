package com.example.eduplatform.controller;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.service.LessonService;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
        name = "Lesson Controller",
        description = "Lesson API"
)
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessLesson(@id)")
    @Operation(
            summary = "Get lesson by ID",
            description = "Returns a lesson by its ID. " +
                    "Only the owner or enrolled students have permission."
    )
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        LessonResponse lessonResponse = lessonService.getById(id);
        return ResponseEntity.ok(lessonResponse);
    }

    @GetMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("@customSecurityExpression.canAccessModule(@moduleId)")
    @Operation(
            summary = "Get all lessons in a module",
            description = "Returns a list of all lessons belonging to the specified module. " +
                    "Only the owner or enrolled students have permission."
    )
    public ResponseEntity<List<LessonResponse>> getAllLessons(@PathVariable Long moduleId) {
        List<LessonResponse> lessons = lessonService.getAllLessons(moduleId);
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#moduleId)")
    @Operation(
            summary = "Create a new lesson",
            description = "Creates a new lesson in the specified module. Only the module owner has permission."
    )
    public ResponseEntity<LessonResponse> createLesson(@PathVariable Long moduleId,
                                                       @Valid @RequestBody LessonCreateRequest lessonCreateRequest) {
        LessonResponse lessonResponse = lessonService.createLesson(moduleId, lessonCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonResponse);
    }

    @PutMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    @Operation(
            summary = "Update a lesson",
            description = "Updates an existing lesson by its ID. Only the owner of the lesson can update it."
    )
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable Long id,
                                                       @Valid @RequestBody LessonUpdateRequest lessonUpdateRequest) {
        LessonResponse lessonResponse = lessonService.updateLesson(id, lessonUpdateRequest);
        return ResponseEntity.ok(lessonResponse);
    }

    @DeleteMapping("/lessons/{id}")
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    @Operation(
            summary = "Delete a lesson",
            description = "Deletes an existing lesson by its ID. Only the owner of the lesson can delete it."
    )
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lessons/{id}/video")
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    @Operation(
            summary = "Upload a video for a lesson",
            description = "Uploads a video file for the specified lesson by its ID. " +
                    "Only the owner of the lesson can upload a video."
    )
    public ResponseEntity<Void> uploadVideo(@PathVariable Long id,
                                            @Valid @ModelAttribute LessonVideo video) {
        lessonService.uploadVideo(id, video);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
