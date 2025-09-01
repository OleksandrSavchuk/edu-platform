package com.example.eduplatform.service;

import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.enums.Role;
import com.example.eduplatform.security.JwtTokenProvider;
import com.example.eduplatform.service.impl.AuthServiceImpl;
import com.example.eduplatform.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldCreateUserWhenDataIsValid() {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("test@example.com")
                .password("123")
                .passwordConfirmation("123")
                .firstName("John")
                .lastName("Doe")
                .role("student")
                .build();

        UserResponse mockUserResponse = UserResponse.builder()
                .id(1L)
                .email("test@example.com")
                .role("STUDENT")
                .build();

        Mockito.when(userService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString())).thenReturn("encoded");
        Mockito.when(userService.createUser(ArgumentMatchers.any(UserRegisterRequest.class))).thenReturn(mockUserResponse);
        Mockito.when(jwtTokenProvider.createAccessToken(ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(Role.class))).thenReturn("accessToken");
        Mockito.when(jwtTokenProvider.createRefreshToken(ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(Role.class))).thenReturn("refreshToken");

        JwtResponse jwtResponse = authService.register(request);

        Assertions.assertNotNull(jwtResponse);
        Assertions.assertEquals("accessToken", jwtResponse.getAccessToken());
        Assertions.assertEquals("refreshToken", jwtResponse.getRefreshToken());
    }


}
