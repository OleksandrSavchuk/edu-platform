package com.example.eduplatform.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Response object containing lesson details")
public class LessonResponse {

    @Schema(
            description = "Unique identifier of the lesson",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Title of the lesson",
            example = "Introduction to Java"
    )
    private String title;

    @Schema(
            description = "Brief description of the lesson",
            example = "Basics of Java syntax and structure"
    )
    private String description;

    @Schema(
            description = "Order/index of the lesson within the module",
            example = "1"
    )
    private Integer lessonIndex;

    @Schema(
            description = "Identifier of the module this lesson belongs to",
            example = "5"
    )
    private Long moduleId;

    @Schema(
            description = "Full content of the lesson",
            example = "Java is a high-level, class-based, object-oriented programming language..."
    )
    private String content;

    @Schema(
            description = "URL of the lesson video, if available",
            example = "https://example.com/videos/java-intro.mp4"
    )
    private String videoUrl;

    @Schema(
            description = "Date and time when the lesson was created",
            example = "2025-08-01 10:15:30"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the lesson was last updated",
            example = "2025-08-03 14:45:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
