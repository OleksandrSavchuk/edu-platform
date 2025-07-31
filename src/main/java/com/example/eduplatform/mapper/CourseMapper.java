package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.entity.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseCreateRequest courseCreateRequest);

    @Mapping(target = "instructorName", source = ".", qualifiedByName = "formatInstructorName")
    CourseResponse toDto(Course course);

    List<CourseResponse> toDto(List<Course> courseList);

    void updateEntityFromDto(CourseUpdateRequest courseUpdateRequest, @MappingTarget Course course);

    @Named("formatInstructorName")
    static String formatInstructorName(Course course) {
        return course.getInstructor().getFirstName() + " " + course.getInstructor().getLastName();
    }

}
