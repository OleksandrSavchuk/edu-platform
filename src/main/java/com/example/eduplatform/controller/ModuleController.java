package com.example.eduplatform.controller;

import com.example.eduplatform.dto.module.ModuleCreateRequest;
import com.example.eduplatform.dto.module.ModuleResponse;
import com.example.eduplatform.dto.module.ModuleUpdateRequest;
import com.example.eduplatform.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable Long id) {
        ModuleResponse moduleResponse = moduleService.getModuleById(id);
        return ResponseEntity.ok(moduleResponse);
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleResponse>> getAllModules(@PathVariable Long courseId) {
        List<ModuleResponse> moduleResponses = moduleService.getAllModules();
        return ResponseEntity.ok(moduleResponses);
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ModuleResponse> createModule(@PathVariable Long courseId,
                                                       @Valid @RequestBody ModuleCreateRequest moduleCreateRequest) {
        ModuleResponse moduleResponse = moduleService.createModule(moduleCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduleResponse);
    }

    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id,
                                                       @Valid @RequestBody ModuleUpdateRequest moduleUpdateRequest) {
        ModuleResponse moduleResponse = moduleService.updateModule(id, moduleUpdateRequest);
        return ResponseEntity.ok(moduleResponse);
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

}
