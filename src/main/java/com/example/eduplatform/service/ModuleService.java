package com.example.eduplatform.service;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.entity.Module;

import java.util.List;

public interface ModuleService {

    ModuleResponse getById(Long id);

    Module getModuleById(Long id);

    List<ModuleResponse> getAllModules(Long courseId);

    ModuleResponse createModule(Long courseId, ModuleCreateRequest moduleCreateRequest);

    ModuleResponse updateModule(Long id, ModuleUpdateRequest moduleUpdateRequest);

    void deleteModule(Long id);

    boolean existsByIdAndCourseId(Long moduleId, Long courseId);

}
