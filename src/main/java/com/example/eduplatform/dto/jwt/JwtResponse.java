package com.example.eduplatform.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "JWT response")
public class JwtResponse {

    @Schema(
            description = "User id",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "User email",
            example = "johndoe@gmail.com"
    )
    private String email;

    @Schema(
            description = "Access token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJ"
                    + "zdWIiOiJqb2huZG9lIiwiaWQiOjEsInJvbGVzIjpb"
                    + "IlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiZXhw"
                    + "IjoxNDY5NDUyOTMyNn0.2hz3feMD0LTdF7_ABYyR"
                    + "JJufPfaZsvUY84VsO6ypNgFX8doWm1MDeh8CJLay"
                    + "zLQHRu7WgwGBUbRqOMwzjr5zlg"
    )
    private String accessToken;

    @Schema(
            description = "Refresh token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJ"
                    + "zdWIiOiJqb2huZG9lIiwiaWQiOjEsInJvbGVzIjpb"
                    + "IlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiZXhw"
                    + "IjoxNDY5NDUyOTMyNn0.2hz3feMD0LTdF7_ABYyR"
                    + "JJufPfaZsvUY84VsO6ypNgFX8doWm1MDeh8CJLay"
                    + "zLQHRu7WgwGBUbRqOMwzjr5zlg"
    )
    private String refreshToken;

}
