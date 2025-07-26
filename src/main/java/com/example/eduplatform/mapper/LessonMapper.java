package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonCreateRequest lessonCreateRequest);

    LessonResponse toDto(Lesson lesson);

    List<LessonResponse> toDto(List<Lesson> lessons);

    void updateEntityFromDto(LessonUpdateRequest lessonUpdateRequest, @MappingTarget Lesson lesson);

}
