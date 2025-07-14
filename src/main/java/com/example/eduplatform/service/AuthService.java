package com.example.eduplatform.service;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserRegisterRequest;

public interface AuthService {

    JwtResponse register(UserRegisterRequest userRegisterRequest);

    JwtResponse login(JwtRequest jwtRequest);

    JwtResponse refreshToken(String refreshToken);

}
