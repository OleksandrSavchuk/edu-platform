package com.example.eduplatform.dto.enrollment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentCreateRequest {

    private Long userId;

    private Long courseId;

}
