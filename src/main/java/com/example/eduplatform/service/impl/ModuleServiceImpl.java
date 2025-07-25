package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.entity.Course;
import com.example.eduplatform.entity.Module;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.ModuleMapper;
import com.example.eduplatform.repository.ModuleRepository;
import com.example.eduplatform.service.CourseService;
import com.example.eduplatform.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final CourseService courseService;

    @Override
    public ModuleResponse getById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module with id " + id + " not found"));
        return moduleMapper.toDto(module);
    }

    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module with id " + id + " not found"));
    }

    @Override
    public List<ModuleResponse> getAllModules(Long courseId) {
        List<Module> modules = moduleRepository.findAllByCourseId(courseId);
        return moduleMapper.toDto(modules);
    }

    @Override
    public ModuleResponse createModule(Long courseId, ModuleCreateRequest moduleCreateRequest) {
        Course course = courseService.getCourseById(courseId);
        Module module = moduleMapper.toEntity(moduleCreateRequest);
        module.setCreatedAt(LocalDateTime.now());
        module.setUpdatedAt(LocalDateTime.now());
        module.setCourse(course);
        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Override
    public ModuleResponse updateModule(Long id, ModuleUpdateRequest moduleUpdateRequest) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module with id " + id + " not found"));
        moduleMapper.updateEntityFromDto(moduleUpdateRequest, module);
        module.setUpdatedAt(LocalDateTime.now());
        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Override
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Module with id " + id + " not found");
        }
        moduleRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdAndCourseId(Long moduleId, Long courseId) {
        return moduleRepository.existsByIdAndCourseId(moduleId, courseId);
    }

}
