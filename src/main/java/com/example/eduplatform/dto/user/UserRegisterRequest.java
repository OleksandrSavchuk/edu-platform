package com.example.eduplatform.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    @NotBlank(message = "Email must not be null.")
    @Email
    private String email;

    @NotBlank(message = "Password must not be null.")
    private String password;

    @NotBlank(message = "Password confirmation must not be null.")
    private String passwordConfirmation;

    @NotBlank(message = "First name must not be null.")
    private String firstName;

    @NotBlank(message = "Last name must not be null.")
    private String lastName;

    @NotBlank(message = "Role must not be null.")
    private String role;
}
