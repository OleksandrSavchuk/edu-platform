package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseCreateRequest courseCreateRequest);

    CourseResponse toDto(Course course);

    List<CourseResponse> toDto(List<Course> courseList);

    void updateEntityFromDto(CourseUpdateRequest courseUpdateRequest, @MappingTarget Course course);

}
