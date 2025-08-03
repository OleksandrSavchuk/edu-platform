package com.example.eduplatform.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating user profile information")
public class UserUpdateRequest {

    @Schema(
            description = "Updated first name of the user",
            example = "Oleksandr"
    )
    @NotBlank(message = "First name must not be null or blank.")
    private String firstName;

    @Schema(
            description = "Updated last name of the user",
            example = "Savchuk"
    )
    @NotBlank(message = "Last name must not be null or blank.")
    private String lastName;

}
