package com.example.eduplatform.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateRequest {

    @NotBlank(message = "Title")
    private String title;

    @NotBlank(message = "Description must be not null.")
    private String description;

}
