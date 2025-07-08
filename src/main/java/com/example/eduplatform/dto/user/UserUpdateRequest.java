package com.example.eduplatform.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "First name must not be null.")
    private String firstName;

    @NotBlank(message = "Last name must not be null.")
    private String lastName;

}
