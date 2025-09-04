package com.example.eduplatform.service;

import com.example.eduplatform.dto.course.CourseCreateRequest;
import com.example.eduplatform.dto.course.CourseResponse;
import com.example.eduplatform.dto.course.CourseUpdateRequest;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.entity.enums.Role;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.CourseMapper;
import com.example.eduplatform.repository.CourseRepository;
import com.example.eduplatform.service.impl.CourseServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUpSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        lenient().when(authentication.getName()).thenReturn("instructor@example.com");
        lenient().when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void shouldReturnCourseResponseWhenInstructorRequestsOwnCourse() {
        User instructor = new User();
        instructor.setId(1L);
        instructor.setEmail("instructor@example.com");
        instructor.setRole(Role.INSTRUCTOR);

        Course course = new Course();
        course.setId(1L);
        course.setInstructor(instructor);

        CourseResponse response = CourseResponse.builder()
                .id(1L)
                .build();

        when(userService.getUserByEmail(instructor.getEmail())).thenReturn(instructor);
        when(courseRepository.findByIdAndInstructorId(course.getId(), instructor.getId()))
                .thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(response);

        CourseResponse result = courseService.getById(course.getId());

        assertEquals(response, result);
        verify(courseRepository).findByIdAndInstructorId(course.getId(), instructor.getId());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCourseNotFoundForInstructor() {
        User instructor = new User();
        instructor.setId(1L);
        instructor.setEmail("instructor@example.com");
        instructor.setRole(Role.INSTRUCTOR);

        when(userService.getUserByEmail(instructor.getEmail())).thenReturn(instructor);
        when(courseRepository.findByIdAndInstructorId(1L, instructor.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getById(1L));
    }

    @Test
    void shouldReturnCourseWhenInstructorRequestsOwnCourse() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(course.getId());

        assertEquals(course, result);
        verify(courseRepository).findById(course.getId());
    }

    @Test
    void shouldReturnAllCoursesForInstructor() {
        User instructor = new User();
        instructor.setId(1L);
        instructor.setEmail("instructor@example.com");
        instructor.setRole(Role.INSTRUCTOR);

        Course course = new Course();
        course.setId(1L);

        CourseResponse courseResponse = CourseResponse.builder()
                .id(1L)
                .build();

        when(userService.getUserByEmail(instructor.getEmail())).thenReturn(instructor);
        when(courseRepository.findAllByInstructorId(instructor.getId())).thenReturn(List.of(course));
        when(courseMapper.toDto(List.of(course))).thenReturn(List.of(courseResponse));

        List<CourseResponse> result = courseService.getAllCourses();

        assertEquals(1, result.size());
        assertEquals(courseResponse, result.getFirst());

        verify(courseRepository).findAllByInstructorId(instructor.getId());
    }

    @Test
    void shouldCreateCourseWhenInstructorValid() {
        User instructor = new User();
        instructor.setId(1L);
        instructor.setEmail("instructor@example.com");
        instructor.setRole(Role.INSTRUCTOR);

        CourseCreateRequest request = CourseCreateRequest.builder()
                .title("")
                .build();

        Course course = new Course();
        course.setId(1L);
        course.setInstructor(instructor);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        CourseResponse response = CourseResponse.builder()
                .id(1L)
                .title("")
                .description("")
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();

        when(userService.getUserByEmail(instructor.getEmail())).thenReturn(instructor);
        when(courseMapper.toEntity(request)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(response);

        CourseResponse result = courseService.createCourse(request);

        assertEquals(response, result);

        verify(courseRepository).save(course);
    }

    @Test
    void shouldUpdateCourseWhenExists() {
        Course course = new Course();
        course.setId(1L);

        CourseUpdateRequest request = CourseUpdateRequest.builder()
                .title("")
                .description("")
                .build();
        CourseResponse response = CourseResponse.builder()
                .id(1L)
                .title("")
                .description("")
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        doNothing().when(courseMapper).updateEntityFromDto(request, course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(response);

        CourseResponse result = courseService.updateCourse(course.getId(), request);

        assertEquals("", result.getTitle());
        assertEquals("", result.getDescription());

        verify(courseMapper).updateEntityFromDto(request, course);
        verify(courseRepository).save(course);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.updateCourse(1L, CourseUpdateRequest.builder().build()));
    }

    @Test
    void shouldDeleteCourseWhenExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.deleteCourse(1L);

        verify(courseRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCourse() {
        when(courseRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(1L));
    }

    @Test
    void shouldReturnTrueWhenCourseExistsByInstructorId() {
        when(courseRepository.existsByIdAndInstructorId(1L, 1L)).thenReturn(true);

        assertTrue(courseService.existsByIdAndInstructorId(1L, 1L));
    }

}
