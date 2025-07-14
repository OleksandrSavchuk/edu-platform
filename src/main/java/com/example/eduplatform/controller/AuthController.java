package com.example.eduplatform.controller;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.jwt.RefreshTokenRequest;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        JwtResponse jwtResponse = authService.register(userRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jwtResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = authService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtResponse jwtResponse = authService.refreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

}
