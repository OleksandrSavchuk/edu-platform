package com.example.eduplatform.service;

import com.example.eduplatform.dto.jwt.JwtRequest;
import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.enums.Role;
import com.example.eduplatform.exception.UserAlreadyExistsException;
import com.example.eduplatform.security.JwtTokenProvider;
import com.example.eduplatform.service.impl.AuthServiceImpl;
import com.example.eduplatform.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        when(userService.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("encoded");
        when(userService.createUser(any(UserRegisterRequest.class))).thenReturn(mockUserResponse);
        when(jwtTokenProvider.createAccessToken(anyLong(),
                anyString(),
                any(Role.class))).thenReturn("accessToken");
        when(jwtTokenProvider.createRefreshToken(anyLong(),
                anyString(),
                any(Role.class))).thenReturn("refreshToken");

        JwtResponse jwtResponse = authService.register(request);

        assertNotNull(jwtResponse);
        assertEquals("accessToken", jwtResponse.getAccessToken());
        assertEquals("refreshToken", jwtResponse.getRefreshToken());

        verify(userService).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(2)).encode("123");
        verify(userService).createUser(any(UserRegisterRequest.class));
        verify(jwtTokenProvider).createAccessToken(anyLong(), anyString(), any(Role.class));
        verify(jwtTokenProvider).createRefreshToken(anyLong(), anyString(), any(Role.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("test@example.com")
                .build();

        when(userService.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));

        verify(userService).existsByEmail(request.getEmail());
        verify(userService, never()).createUser(any());
    }

    @Test
    void shouldThrowExceptionWhenInvalidRole() {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("test@example.com")
                .role("ADMIN")
                .build();

        when(userService.existsByEmail(request.getEmail())).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> authService.register(request));

        verify(userService).existsByEmail(request.getEmail());
        verify(userService, never()).createUser(any());
    }

    @Test
    void shouldReturnTokensWhenLoginSuccessful() {
        JwtRequest request = JwtRequest.builder()
                .email("test@example.com")
                .password("123")
                .build();
        UserResponse mockUser = UserResponse.builder()
                .id(1L)
                .email("test@example.com")
                .role("STUDENT")
                .build();

        when(authenticationManager.authenticate(
                        any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        when(userService.getUserResponseByEmail(request.getEmail())).thenReturn(mockUser);
        when(jwtTokenProvider.createAccessToken(anyLong(),
                anyString(),
                any(Role.class))).thenReturn("accessToken");
        when(jwtTokenProvider.createRefreshToken(anyLong(),
                anyString(),
                any(Role.class))).thenReturn("refreshToken");

        JwtResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(request.getEmail(), response.getEmail());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).getUserResponseByEmail(request.getEmail());
        verify(jwtTokenProvider).createAccessToken(anyLong(), anyString(), any(Role.class));
        verify(jwtTokenProvider).createRefreshToken(anyLong(), anyString(), any(Role.class));
    }

    @Test
    void shouldReturnTokensWhenRefreshTokenIsValid() {
        when(jwtTokenProvider.refreshTokens("refreshToken")).thenReturn(
                JwtResponse.builder().id(1L).email("refresh@example.com").accessToken("newAccess").refreshToken("newRefresh").build()
        );

        JwtResponse response = authService.refreshToken("refreshToken");

        assertNotNull(response);
        assertEquals("refresh@example.com", response.getEmail());
        assertEquals("newAccess", response.getAccessToken());
        assertEquals("newRefresh", response.getRefreshToken());

        verify(jwtTokenProvider).refreshTokens("refreshToken");
    }

}
