package com.example.eduplatform.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating an existing course")
public class CourseUpdateRequest {

    @Schema(
            description = "The updated title of the course",
            example = "Advanced Java Programming"
    )
    @NotBlank(message = "Title must not be blank.")
    private String title;

    @Schema(
            description = "The updated description of the course",
            example = "This course covers advanced Java topics including streams, concurrency, and JVM internals"
    )
    @NotBlank(message = "Description must not be blank.")
    private String description;

}
