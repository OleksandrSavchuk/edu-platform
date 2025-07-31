package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    Module toEntity(ModuleCreateRequest moduleCreateRequest);

    @Mapping(target = "courseId", source = "course.id")
    ModuleResponse toDto(Module module);

    List<ModuleResponse> toDto(List<Module> modules);

    void updateEntityFromDto(ModuleUpdateRequest moduleUpdateRequest, @MappingTarget Module module);

}
