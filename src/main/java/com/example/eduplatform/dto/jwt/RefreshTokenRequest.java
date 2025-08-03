package com.example.eduplatform.dto.jwt;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request for refresh")
public class RefreshTokenRequest {

    @Schema(
            description = "Refresh token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJ"
                    + "zdWIiOiJqb2huZG9lIiwiaWQiOjEsInJvbGVzIjpb"
                    + "IlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiZXhw"
                    + "IjoxNDY5NDUyOTMyNn0.2hz3feMD0LTdF7_ABYyR"
                    + "JJufPfaZsvUY84VsO6ypNgFX8doWm1MDeh8CJLay"
                    + "zLQHRu7WgwGBUbRqOMwzjr5zlg"
    )
    @NotBlank(message = "Refresh token must not be null.")
    private String refreshToken;

}
