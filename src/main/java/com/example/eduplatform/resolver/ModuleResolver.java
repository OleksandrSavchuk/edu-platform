package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ModuleResolver {

    private final ModuleService moduleService;

    @QueryMapping
    @PreAuthorize("@customSecurityExpression.canAccessModule(#id)")
    public ModuleResponse getModuleById(@Argument Long id) {
        return moduleService.getById(id);
    }

    @QueryMapping
    @PreAuthorize("@customSecurityExpression.canAccessCourse(#courseId)")
    public List<ModuleResponse> getAllModules(@Argument Long courseId) {
        return moduleService.getAllModules(courseId);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#courseId)")
    public ModuleResponse createModule(@Argument Long courseId, @Argument ModuleCreateRequest moduleCreateRequest) {
        return moduleService.createModule(courseId, moduleCreateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#id)")
    public ModuleResponse updateModule(@Argument Long id, @Argument ModuleUpdateRequest moduleUpdateRequest) {
        return moduleService.updateModule(id, moduleUpdateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#id)")
    public Boolean deleteModule(@Argument Long id) {
        moduleService.deleteModule(id);
        return true;
    }

}
