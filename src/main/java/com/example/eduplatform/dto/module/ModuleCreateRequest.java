package com.example.eduplatform.dto.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleCreateRequest {

    @NotBlank(message = "Title must be not null.")
    private String title;

    @NotBlank(message = "Description must be not null.")
    private String description;

    @NotNull(message = "Module index must be not null.")
    private Integer moduleIndex;

}
