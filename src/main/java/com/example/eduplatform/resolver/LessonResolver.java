package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.lesson.LessonCreateRequest;
import com.example.eduplatform.dto.lesson.LessonResponse;
import com.example.eduplatform.dto.lesson.LessonUpdateRequest;
import com.example.eduplatform.dto.lesson.LessonVideo;
import com.example.eduplatform.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonResolver {

    private final LessonService lessonService;

    @QueryMapping
    @PreAuthorize("@customSecurityExpression.canAccessLesson(@id)")
    public LessonResponse getLessonById(@Argument Long id) {
        return lessonService.getById(id);
    }

    @QueryMapping
    @PreAuthorize("@customSecurityExpression.canAccessModule(@moduleId)")
    public List<LessonResponse> getAllLessons(@Argument Long moduleId) {
        return lessonService.getAllLessons(moduleId);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#moduleId)")
    public LessonResponse createLesson(@Argument Long moduleId, @Argument LessonCreateRequest lessonCreateRequest) {
        return lessonService.createLesson(moduleId, lessonCreateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    public LessonResponse updateLesson(@Argument Long id, @Argument LessonUpdateRequest lessonUpdateRequest) {
        return lessonService.updateLesson(id, lessonUpdateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    public Boolean deleteLesson(@Argument Long id) {
        lessonService.deleteLesson(id);
        return true;
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isLessonOwner(#id)")
    public Boolean uploadVideo(@Argument Long id, @Argument LessonVideo video) {
        lessonService.uploadVideo(id, video);
        return true;
    }

}
