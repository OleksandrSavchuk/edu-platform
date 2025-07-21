package com.example.eduplatform.service;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;

import java.util.List;

public interface ModuleService {

    ModuleResponse getModuleById(Long id);

    List<ModuleResponse> getAllModules();

    ModuleResponse createModule(ModuleCreateRequest moduleCreateRequest);

    ModuleResponse updateModule(Long id, ModuleUpdateRequest moduleUpdateRequest);

    void deleteModule(Long id);

}
