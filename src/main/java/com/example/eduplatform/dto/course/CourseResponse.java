package com.example.eduplatform.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Jacksonized
@Schema(description = "Response object containing course details")
public class CourseResponse {

    @Schema(
            description = "Course id",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "The title of the course",
            example = "Spring Boot Fundamentals"
    )
    private String title;

    @Schema(
            description = "A short description of the course",
            example = "Learn how to build RESTful APIs using Spring Boot"
    )
    private String description;

    @Schema(
            description = "Full name of the instructor teaching the course",
            example = "John Smith"
    )
    private String instructorName;

    @Schema(
            description = "Date and time when the course was created",
            example = "2025-08-01 10:15:30"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the course was last updated",
            example = "2025-08-03 14:45:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
