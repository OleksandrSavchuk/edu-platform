package com.example.eduplatform.security.expression;

import com.example.eduplatform.entity.Module;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.service.CourseService;
import com.example.eduplatform.service.ModuleService;
import com.example.eduplatform.service.UserService;
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


    public boolean canAccessUser(Long id) {
        return getCurrentUser().getId().equals(id);
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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

}
