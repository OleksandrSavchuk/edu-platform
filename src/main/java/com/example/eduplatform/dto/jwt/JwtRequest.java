package com.example.eduplatform.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(
            description = "Email",
            example = "johndoe@gmail.com"
    )
    @NotBlank(message = "Email must not be null.")
    private String email;

    @Schema(
            description = "Password",
            example = "12345"
    )
    @NotBlank(message = "Password must not be null.")
    private String password;

}
