package com.example.eduplatform.controller;

import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(
        name = "User Controller",
        description = "User API"
)
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.canAccessUser(#id)")
    @Operation(
            summary = "Get user by ID",
            description = "Return user details by their ID. Only accessible by the user or an admin."
    )
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users. Accessible only by admin."
    )
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ResponseEntity.ok(userResponses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    @Operation(
            summary = "Update user",
            description = "Updates the user's information. Only accessible by the user themself."
    )
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    @Operation(
            summary = "Delete user",
            description = "Deletes a user account. Only accessible by the user themself.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
