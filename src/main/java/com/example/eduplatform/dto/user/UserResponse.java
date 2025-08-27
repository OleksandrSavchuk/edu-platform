package com.example.eduplatform.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Response object containing user details")
public class UserResponse {

    @Schema(
            description = "Unique identifier of the user",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "User's email address",
            example = "user@example.com"
    )
    private String email;

    @Schema(
            description = "User's first name",
            example = "Oleksandr"
    )
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Savchuk"
    )
    private String lastName;

    @Schema(
            description = "User role. Could be STUDENT, INSTRUCTOR, or ADMIN.",
            example = "STUDENT"
    )
    private String role;

    @Schema(
            description = "Date and time when the user account was created",
            example = "2025-08-03 12:30:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

}
