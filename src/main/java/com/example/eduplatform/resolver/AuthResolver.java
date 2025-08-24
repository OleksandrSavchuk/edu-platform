package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.jwt.RefreshTokenRequest;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthResolver {

    private final AuthService authService;

    @MutationMapping
    public JwtResponse register(@Argument UserRegisterRequest userRegisterRequest) {
        return authService.register(userRegisterRequest);
    }

    @MutationMapping
    public JwtResponse login(@Argument JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @MutationMapping
    public JwtResponse refresh(@Argument RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest.getRefreshToken());
    }

}
