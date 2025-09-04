package com.example.eduplatform.service;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.exception.DuplicateModuleIndexException;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.ModuleMapper;
import com.example.eduplatform.repository.ModuleRepository;
import com.example.eduplatform.service.impl.ModuleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceImplTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private ModuleMapper moduleMapper;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    @Test
    void shouldReturnModuleResponseWhenModuleExists() {
        Module module = new Module();
        module.setId(1L);
        ModuleResponse response = ModuleResponse.builder()
                .id(1L)
                .build();

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
        when(moduleMapper.toDto(module)).thenReturn(response);

        ModuleResponse result = moduleService.getById(1L);

        assertEquals(response, result);

        verify(moduleRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenModuleNotFoundById() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moduleService.getById(1L));
    }

    @Test
    void shouldReturnModuleEntityWhenModuleExists() {
        Module module = new Module();
        module.setId(1L);

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        Module result = moduleService.getModuleById(1L);

        assertEquals(module, result);

        verify(moduleRepository).findById(1L);
    }

    @Test
    void shouldReturnAllModulesForCourse() {
        Long courseId = 1L;
        Module module = new Module();
        ModuleResponse response = ModuleResponse.builder().build();

        when(moduleRepository.findAllByCourseId(courseId)).thenReturn(List.of(module));
        when(moduleMapper.toDto(List.of(module))).thenReturn(List.of(response));

        List<ModuleResponse> result = moduleService.getAllModules(courseId);

        assertEquals(1, result.size());
        assertEquals(response, result.getFirst());

        verify(moduleRepository).findAllByCourseId(courseId);
    }

    @Test
    void shouldCreateModuleWhenIndexNotExists() {
        Long courseId = 1L;
        ModuleCreateRequest request = ModuleCreateRequest.builder().build();
        request.setModuleIndex(1);

        Course course = new Course();
        course.setId(courseId);

        Module module = new Module();
        ModuleResponse response = ModuleResponse.builder().build();

        when(moduleRepository.existsByCourseIdAndModuleIndex(courseId, 1)).thenReturn(false);
        when(courseService.getCourseById(courseId)).thenReturn(course);
        when(moduleMapper.toEntity(request)).thenReturn(module);
        when(moduleRepository.save(module)).thenReturn(module);
        when(moduleMapper.toDto(module)).thenReturn(response);

        ModuleResponse result = moduleService.createModule(courseId, request);

        assertEquals(response, result);

        verify(moduleRepository).save(module);
    }

    @Test
    void shouldThrowWhenCreatingModuleWithDuplicateIndex() {
        Long courseId = 1L;
        ModuleCreateRequest request = ModuleCreateRequest.builder().build();
        request.setModuleIndex(1);

        when(moduleRepository.existsByCourseIdAndModuleIndex(courseId, 1)).thenReturn(true);

        assertThrows(DuplicateModuleIndexException.class,
                () -> moduleService.createModule(courseId, request));
    }

    @Test
    void shouldUpdateModuleWhenValid() {
        Long moduleId = 1L;
        ModuleUpdateRequest request = ModuleUpdateRequest.builder().build();
        request.setModuleIndex(2);

        Course course = new Course();
        course.setId(1L);

        Module module = new Module();
        module.setId(moduleId);
        module.setCourse(course);

        ModuleResponse response = ModuleResponse.builder().build();

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));
        when(moduleRepository.existsByCourseIdAndModuleIndex(course.getId(), 2)).thenReturn(false);
        when(moduleRepository.save(module)).thenReturn(module);
        when(moduleMapper.toDto(module)).thenReturn(response);

        ModuleResponse result = moduleService.updateModule(moduleId, request);

        assertEquals(response, result);

        verify(moduleMapper).updateEntityFromDto(request, module);
        verify(moduleRepository).save(module);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingModule() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> moduleService.updateModule(1L, ModuleUpdateRequest.builder().build()));
    }

    @Test
    void shouldThrowWhenUpdatingWithDuplicateIndex() {
        Long moduleId = 1L;
        ModuleUpdateRequest request = ModuleUpdateRequest.builder().build();
        request.setModuleIndex(5);

        Course course = new Course();
        course.setId(1L);

        Module module = new Module();
        module.setId(moduleId);
        module.setCourse(course);

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));
        when(moduleRepository.existsByCourseIdAndModuleIndex(course.getId(), 5)).thenReturn(true);

        assertThrows(DuplicateModuleIndexException.class,
                () -> moduleService.updateModule(moduleId, request));
    }

    @Test
    void shouldDeleteModuleWhenExists() {
        when(moduleRepository.existsById(1L)).thenReturn(true);

        moduleService.deleteModule(1L);

        verify(moduleRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingModule() {
        when(moduleRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> moduleService.deleteModule(1L));
    }

    @Test
    void shouldReturnTrueWhenModuleExistsByIdAndCourseId() {
        when(moduleRepository.existsByIdAndCourseId(1L, 2L)).thenReturn(true);

        assertTrue(moduleService.existsByIdAndCourseId(1L, 2L));
    }

    @Test
    void shouldReturnFalseWhenModuleDoesNotExistByIdAndCourseId() {
        when(moduleRepository.existsByIdAndCourseId(1L, 2L)).thenReturn(false);

        assertFalse(moduleService.existsByIdAndCourseId(1L, 2L));
    }
}
