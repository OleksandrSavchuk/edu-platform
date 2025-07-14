package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.security.JwtTokenProvider;
import com.example.eduplatform.service.AuthService;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse register(UserRegisterRequest userRegisterRequest) {
        return null;
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        return null;
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        return null;
    }

}
