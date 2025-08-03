package com.example.eduplatform.dto.enrollment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Enrollment response")
public class EnrollmentResponse {

    @Schema(
            description = "Enrollment id",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "User id",
            example = "1"
    )
    private Long userId;

    @Schema(
            description = "Course id",
            example = "1"
    )
    private Long courseId;

    @Schema(
            description = "Date and time when the user was enrolled",
            example = "2025-08-03 14:23:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrolledAt;

}
