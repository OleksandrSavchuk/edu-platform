package com.example.eduplatform.dto.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Response object containing module details")
public class ModuleResponse {

    @Schema(
            description = "Unique identifier of the module",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Title of the module",
            example = "Getting Started with Spring"
    )
    private String title;

    @Schema(
            description = "Description of the module",
            example = "This module introduces the core concepts of the Spring Framework"
    )
    private String description;

    @Schema(
            description = "Index/order of the module within the course",
            example = "1"
    )
    private Integer moduleIndex;

    @Schema(
            description = "ID of the course to which this module belongs",
            example = "1"
    )
    private Long courseId;

    @Schema(
            description = "Date and time when the module was created",
            example = "2025-08-01 12:00:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the module was last updated",
            example = "2025-08-03 18:45:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
