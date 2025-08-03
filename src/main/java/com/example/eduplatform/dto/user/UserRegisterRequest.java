package com.example.eduplatform.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Request body for registering a new user")
public class UserRegisterRequest {

    @Schema(
            description = "User's email address",
            example = "user@example.com"
    )
    @NotBlank(message = "Email must not be null.")
    @Email(message = "Email should be valid.")
    private String email;

    @Schema(
            description = "User's password",
            example = "StrongP@ssw0rd"
    )
    @NotBlank(message = "Password must not be null.")
    private String password;

    @Schema(
            description = "Password confirmation. Must match the password.",
            example = "StrongP@ssw0rd"
    )
    @NotBlank(message = "Password confirmation must not be null.")
    private String passwordConfirmation;

    @Schema(
            description = "User's first name",
            example = "Oleksandr"
    )
    @NotBlank(message = "First name must not be null.")
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Savchuk"
    )
    @NotBlank(message = "Last name must not be null.")
    private String lastName;

    @Schema(
            description = "User role. Typically 'STUDENT' or 'INSTRUCTOR'.",
            example = "STUDENT"
    )
    @NotBlank(message = "Role must not be null.")
    private String role;
}
