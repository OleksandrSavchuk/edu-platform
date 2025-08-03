package com.example.eduplatform.controller;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
        name = "Module Controller",
        description = "Module API"
)
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping("/modules/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessModule(#id)")
    @Operation(
            summary = "Get module by ID",
            description = "Returns detailed information about a specific module by its ID. " +
                    "Accessible only if the user has access to the module."
    )
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable Long id) {
        ModuleResponse moduleResponse = moduleService.getById(id);
        return ResponseEntity.ok(moduleResponse);
    }

    @GetMapping("/courses/{courseId}/modules")
    @PreAuthorize("@customSecurityExpression.canAccessCourse(#courseId)")
    @Operation(
            summary = "Get all modules of a course",
            description = "Returns a list of all modules belonging to a specific course. " +
                    "Accessible only if the user has access to the course."
    )
    public ResponseEntity<List<ModuleResponse>> getAllModules(@PathVariable Long courseId) {
        List<ModuleResponse> moduleResponses = moduleService.getAllModules(courseId);
        return ResponseEntity.ok(moduleResponses);
    }

    @PostMapping("/courses/{courseId}/modules")
    @PreAuthorize("@customSecurityExpression.isCourseOwner(#courseId)")
    @Operation(
            summary = "Create a new module",
            description = "Creates a new module within the specified course. " +
                    "Only accessible by the course owner."
    )
    public ResponseEntity<ModuleResponse> createModule(@PathVariable Long courseId,
                                                       @Valid @RequestBody ModuleCreateRequest moduleCreateRequest) {
        ModuleResponse moduleResponse = moduleService.createModule(courseId, moduleCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduleResponse);
    }

    @PutMapping("/modules/{id}")
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#id)")
    @Operation(
            summary = "Update a module",
            description = "Updates an existing module. Only accessible by the module owner."
    )
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id,
                                                       @Valid @RequestBody ModuleUpdateRequest moduleUpdateRequest) {
        ModuleResponse moduleResponse = moduleService.updateModule(id, moduleUpdateRequest);
        return ResponseEntity.ok(moduleResponse);
    }

    @DeleteMapping("/modules/{id}")
    @PreAuthorize("@customSecurityExpression.isModuleOwner(#id)")
    @Operation(
            summary = "Delete a module",
            description = "Deletes an existing module. Only accessible by the module owner."
    )
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

}
