package com.example.eduplatform.security.expression;

import com.example.eduplatform.entity.Lesson;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;
    private final CourseService courseService;
    private final ModuleService moduleService;
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;


    public boolean canAccessUser(Long id) {
        return getCurrentUser().getId().equals(id);
    }

    public boolean canAccessCourse(Long courseId) {
        Long userId = getCurrentUser().getId();
        return isInstructorOrEnrolled(courseId, userId);
    }

    public boolean canAccessModule(Long moduleId) {
        Long userId = getCurrentUser().getId();
        Module module = moduleService.getModuleById(moduleId);
        Long courseId = module.getCourse().getId();
        return isInstructorOrEnrolled(courseId, userId);
    }

    public boolean canAccessLesson(Long lessonId) {
        Long userId = getCurrentUser().getId();
        Lesson lesson = lessonService.getLessonById(lessonId);
        Long courseId = lesson.getModule().getCourse().getId();
        return isInstructorOrEnrolled(courseId, userId);
    }

    public boolean isCourseOwner(Long courseId) {
        Long instructorId = getCurrentUser().getId();
        return courseService.existsByIdAndInstructorId(courseId, instructorId);
    }

    public boolean isModuleOwner(Long moduleId) {
        Long instructorId = getCurrentUser().getId();
        Module module = moduleService.getModuleById(moduleId);
        Long courseId = module.getCourse().getId();
        return courseService.existsByIdAndInstructorId(courseId, instructorId);
    }

    public boolean isLessonOwner(Long lessonId) {
        Long instructorId = getCurrentUser().getId();
        Lesson lesson = lessonService.getLessonById(lessonId);
        Long courseId = lesson.getModule().getCourse().getId();
        return courseService.existsByIdAndInstructorId(courseId, instructorId);
    }

    public boolean isEnrolled(Long courseId) {
        Long userId = getCurrentUser().getId();
        return enrollmentService.existsByCourseIdAndUserId(courseId, userId);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

    private boolean isInstructorOrEnrolled(Long courseId, Long userId) {
        return enrollmentService.existsByCourseIdAndUserId(courseId, userId) ||
                courseService.existsByIdAndInstructorId(courseId, userId);
    }

}
