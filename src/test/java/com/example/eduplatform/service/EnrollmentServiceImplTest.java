package com.example.eduplatform.service;

import com.example.eduplatform.dto.enrollment.EnrollmentResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.Enrollment;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.AlreadyEnrolledException;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.EnrollmentMapper;
import com.example.eduplatform.mapper.UserMapper;
import com.example.eduplatform.repository.EnrollmentRepository;
import com.example.eduplatform.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUpSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        lenient().when(authentication.getName()).thenReturn("test@example.com");
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldReturnEnrollmentsForCurrentUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setUser(user);
        enrollment.setEnrolledAt(LocalDateTime.now());

        EnrollmentResponse enrollmentResponse = EnrollmentResponse.builder()
                .id(1L)
                .userId(1L)
                .courseId(1L)
                .enrolledAt(LocalDateTime.now())
                .build();

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(enrollmentRepository.findAllByUserId(user.getId())).thenReturn(List.of(enrollment));
        when(enrollmentMapper.toDto(List.of(enrollment))).thenReturn(List.of(enrollmentResponse));

        List<EnrollmentResponse> result = enrollmentService.getEnrollmentsForCurrentUser();

        assertEquals(1, result.size());
        assertEquals(enrollmentResponse, result.getFirst());
    }

    @Test
    void shouldReturnUsersByCourse() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setUser(user);
        enrollment.setCourse(course);

        when(enrollmentRepository.findAllByCourseId(course.getId())).thenReturn(List.of(enrollment));
        when(userMapper.toDto(user)).thenReturn(response);

        List<UserResponse> result = enrollmentService.getUsersByCourse(course.getId());

        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.getFirst().getEmail());
    }

    @Test
    void shouldCreateEnrollmentWhenNotExists() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        EnrollmentResponse enrollmentResponse = EnrollmentResponse.builder()
                .id(1L)
                .userId(1L)
                .courseId(1L)
                .enrolledAt(LocalDateTime.now())
                .build();

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(enrollmentRepository.existsByCourseIdAndUserId(course.getId(), user.getId())).thenReturn(false);
        when(courseService.getCourseById(course.getId())).thenReturn(course);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        when(enrollmentMapper.toDto(enrollment)).thenReturn(enrollmentResponse);

        EnrollmentResponse result = enrollmentService.createEnrollment(course.getId());

        assertEquals(enrollmentResponse, result);
    }

    @Test
    void shouldThrowAlreadyEnrolledExceptionWhenEnrollmentExists() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Course course = new Course();
        course.setId(1L);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(enrollmentRepository.existsByCourseIdAndUserId(course.getId(), user.getId())).thenReturn(true);

        assertThrows(AlreadyEnrolledException.class, () -> enrollmentService.createEnrollment(course.getId()));
    }

    @Test
    void shouldDeleteEnrollmentWhenExists() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setUser(user);
        enrollment.setCourse(course);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(enrollmentRepository.existsByCourseIdAndUserId(course.getId(), user.getId())).thenReturn(true);
        when(enrollmentRepository.findByCourseIdAndUserId(course.getId(), user.getId())).thenReturn(enrollment);

        enrollmentService.deleteEnrollment(course.getId());

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEnrollment() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Course course = new Course();
        course.setId(1L);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(enrollmentRepository.existsByCourseIdAndUserId(course.getId(), user.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.deleteEnrollment(course.getId()));
    }
}
