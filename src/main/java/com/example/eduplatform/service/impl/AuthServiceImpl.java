package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.enums.Role;
import com.example.eduplatform.exception.UserAlreadyExistsException;
import com.example.eduplatform.security.JwtTokenProvider;
import com.example.eduplatform.service.AuthService;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        if (userService.existsByEmail(userRegisterRequest.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + userRegisterRequest.getEmail());
        }
        userRegisterRequest.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRegisterRequest.setRole(userRegisterRequest.getRole().trim().toUpperCase());
        UserResponse userResponse = userService.createUser(userRegisterRequest);
        return createJwtResponse(userResponse);
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword())
        );

        UserResponse userResponse = userService.getUserResponseByEmail(jwtRequest.getEmail());
        return createJwtResponse(userResponse);
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        return jwtTokenProvider.refreshTokens(refreshToken);
    }

    private JwtResponse createJwtResponse(UserResponse userResponse) {
        return JwtResponse.builder()
                .id(userResponse.getId())
                .email(userResponse.getEmail())
                .accessToken(
                        jwtTokenProvider.createAccessToken(
                                userResponse.getId(),
                                userResponse.getEmail(),
                                Role.valueOf(userResponse.getRole())
                        )
                )
                .refreshToken(
                        jwtTokenProvider.createRefreshToken(
                                userResponse.getId(),
                                userResponse.getEmail(),
                                Role.valueOf(userResponse.getRole())
                        )
                )
                .build();
    }

}
