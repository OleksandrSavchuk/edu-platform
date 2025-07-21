package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.mapper.ModuleMapper;
import com.example.eduplatform.repository.ModuleRepository;
import com.example.eduplatform.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository repository;

    private final ModuleMapper mapper;

    @Override
    public ModuleResponse getModuleById(Long id) {
        return null;
    }

    @Override
    public List<ModuleResponse> getAllModules() {
        return List.of();
    }

    @Override
    public ModuleResponse createModule(ModuleCreateRequest moduleCreateRequest) {
        return null;
    }

    @Override
    public ModuleResponse updateModule(Long id, ModuleUpdateRequest moduleUpdateRequest) {
        return null;
    }

    @Override
    public void deleteModule(Long id) {

    }

}
