package com.example.eduplatform.service;

import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.UserMapper;
import com.example.eduplatform.repository.UserRepository;
import com.example.eduplatform.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Test
    void shouldReturnUserResponseWhenGetByIdAndUserExists() {
        User user = new User();
        user.setId(1L);
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponse);

        UserResponse result = userService.getById(1L);

        assertEquals(userResponse, result);
        verify(userRepository).findById(1L);

    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenGetByIdAndUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    void shouldReturnAllUsersWhenGetAllUsers() {
        List<User> users = List.of(new User(), new User());
        List<UserResponse> responses = List.of(UserResponse.builder().build(), UserResponse.builder().build());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(responses);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(responses, result);

        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserResponseWhenCreateUser() {
        UserRegisterRequest request = UserRegisterRequest.builder().build();
        User user = new User();
        user.setId(1L);
        UserResponse userResponse = UserResponse.builder().id(1L).build();

        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userResponse);

        UserResponse result = userService.createUser(request);

        assertEquals(userResponse, result);
        verify(userRepository).save(user);
    }

    @Test
    void shouldReturnUserResponseWhenUpdateUser() {
        Long userId = 1L;
        UserUpdateRequest request = UserUpdateRequest.builder().build();
        User existingUser = new User();
        User savedUser = new User();
        UserResponse userResponse = UserResponse.builder().build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userMapper).updateEntityFromDto(request, existingUser);
        when(userRepository.save(existingUser)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userResponse);

        UserResponse result = userService.updateUser(userId, request);

        assertEquals(userResponse, result);
        verify(userRepository).save(existingUser);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdateUserAndUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(1L, UserUpdateRequest.builder().build()));
    }

    @Test
    void shouldReturnUserResponseWhenGetUserResponseByEmailAndUserExists() {
        String email = "test@example.com";
        User user = new User();
        UserResponse userResponse = UserResponse.builder().build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserResponseByEmail(email);

        assertEquals(result, userResponse);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenGetUserResponseByEmailAndUserDoesNotExist() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserResponseByEmail("test@example.com"));
    }

    @Test
    void shouldReturnUserWhenGetUserResponseByEmailAndUserExists() {
        String email = "test@example.com";
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);

        assertEquals(result, user);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenGetUserByEmailAndUserDoesNotExist() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("test@example.com"));
    }

    @Test
    void shouldReturnTrueWhenExistByEmailAndUserExists() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(userService.existsByEmail(email));
    }

    @Test
    void shouldReturnFalseWhenExistByEmailAndUserDoesNotExist() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);

        assertFalse(userService.existsByEmail(email));
    }

    @Test
    void shouldDeleteUserWhenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
        verify(cacheManager).getCache("UserService::getByEmail");
        verify(cacheManager).getCache("UserService::getUserByEmail");
        verify(cacheManager).getCache("UserService::existsByEmail");
        verify(cache, times(3)).evict("test@example.com");
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeleteUserAndUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
