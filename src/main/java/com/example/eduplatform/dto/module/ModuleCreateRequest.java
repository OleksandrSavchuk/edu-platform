package com.example.eduplatform.dto.module;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Request body for creating a new module")
public class ModuleCreateRequest {

    @Schema(
            description = "Title of the module",
            example = "Introduction to Spring"
    )
    @NotBlank(message = "Title must not be null or blank.")
    private String title;

    @Schema(
            description = "Description of the module",
            example = "This module covers the basics of Spring Framework"
    )
    @NotBlank(message = "Description must not be null or blank.")
    private String description;

    @Schema(
            description = "Index/order of the module within the course",
            example = "1"
    )
    @NotNull(message = "Module index must not be null.")
    private Integer moduleIndex;

}
