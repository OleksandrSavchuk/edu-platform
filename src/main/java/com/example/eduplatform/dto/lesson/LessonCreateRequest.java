package com.example.eduplatform.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Request body for creating a new lesson")
public class LessonCreateRequest {

    @Schema(
            description = "Title of the lesson",
            example = "Introduction to Java"
    )
    @NotBlank(message = "Title must not be null or blank.")
    private String title;

    @Schema(
            description = "Short description of the lesson",
            example = "Basics of Java syntax and structure"
    )
    @NotBlank(message = "Description must not be null or blank.")
    private String description;

    @Schema(
            description = "Content of the lesson, can be text or markup",
            example = "Java is a high-level programming language..."
    )
    @NotBlank(message = "Content must not be null or blank.")
    private String content;

    @Schema(
            description = "URL to the lesson's video, if available",
            example = "https://example.com/videos/java-intro.mp4"
    )
    private String videoUrl;

    @Schema(
            description = "Index/order of the lesson within the module",
            example = "1"
    )
    @NotNull(message = "Lesson index must not be null.")
    private Integer lessonIndex;

}
