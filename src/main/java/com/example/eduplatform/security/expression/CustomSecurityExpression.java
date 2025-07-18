package com.example.eduplatform.security.expression;

import com.example.eduplatform.entity.User;
import com.example.eduplatform.service.CourseService;
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

    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return user != null && user.getId().equals(id);
    }

    public boolean isCourseOwner(Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        Long instructorId = user.getId();
        return courseService.existsByIdAndInstructorId(courseId, instructorId);
    }

}
