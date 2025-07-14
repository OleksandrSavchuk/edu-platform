package com.example.eduplatform.dto.jwt;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token must not be null.")
    private String refreshToken;

}
