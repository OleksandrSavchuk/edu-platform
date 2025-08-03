package com.example.eduplatform.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for creating a new course")
public class CourseCreateRequest {

    @Schema(
            description = "The title of the course",
            example = "Java Programming for Beginners"
    )
    @NotBlank(message = "Title must be not null.")
    private String title;

    @Schema(
            description = "A short description of the course",
            example = "This course introduces the basics of Java programming"
    )
    @NotBlank(message = "Description must be not null.")
    private String description;

}
