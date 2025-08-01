package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.entity.Enrollment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentResponse toDto(Enrollment enrollment);

    List<EnrollmentResponse> toDto(List<Enrollment> enrollments);

}
