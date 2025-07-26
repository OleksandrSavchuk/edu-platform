package com.example.eduplatform.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonCreateRequest {

    @NotBlank(message = "Title must be not null.")
    private String title;

    @NotBlank(message = "Description must be not null.")
    private String description;

    @NotBlank(message = "Content must be not null.")
    private String content;

    private String videoUrl;

    @NotNull(message = "Module ID must be not null.")
    private Long moduleId;

    @NotNull(message = "Lesson index must be not null.")
    private Integer lessonIndex;

}
