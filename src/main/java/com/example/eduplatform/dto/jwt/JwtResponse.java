package com.example.eduplatform.dto.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;

}
