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
@Schema(description = "Request body for updating an existing lesson")
public class LessonUpdateRequest {

    @Schema(
            description = "Updated title of the lesson",
            example = "Advanced Java Concepts"
    )
    @NotBlank(message = "Title must not be null or blank.")
    private String title;

    @Schema(
            description = "Updated description of the lesson",
            example = "In-depth look at Java Streams and Lambdas"
    )
    @NotBlank(message = "Description must not be null or blank.")
    private String description;

    @Schema(
            description = "Updated content of the lesson",
            example = "Streams are sequences of elements supporting sequential and parallel aggregate operations..."
    )
    @NotBlank(message = "Content must not be null or blank.")
    private String content;

    @Schema(
            description = "URL to the updated lesson video, if applicable",
            example = "https://example.com/videos/advanced-java.mp4"
    )
    private String videoUrl;

    @Schema(
            description = "Updated lesson index/order within the module",
            example = "2"
    )
    @NotNull(message = "Lesson index must not be null.")
    private Integer lessonIndex;

}
