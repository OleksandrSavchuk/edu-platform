package com.example.eduplatform.dto.module;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating an existing module")
public class ModuleUpdateRequest {

    @Schema(
            description = "Updated title of the module",
            example = "Advanced Spring Features"
    )
    @NotBlank(message = "Title must not be null or blank.")
    private String title;

    @Schema(
            description = "Updated description of the module",
            example = "Covers advanced topics like AOP, Security, and Spring Data"
    )
    @NotBlank(message = "Description must not be null or blank.")
    private String description;

    @Schema(
            description = "Updated index/order of the module within the course",
            example = "2"
    )
    @NotNull(message = "Module index must not be null.")
    private Integer moduleIndex;

}
