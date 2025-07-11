package com.example.eduplatform.dto.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRequest {

    @NotBlank(message = "Email must not be null.")
    private String email;

    @NotBlank(message = "Password must not be null.")
    private String password;

}
