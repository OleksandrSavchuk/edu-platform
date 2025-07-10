package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.repository.UserRepository;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<UserResponse> getAllUsers() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponse createUser(UserRegisterRequest userRegisterRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User getUserByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean existsByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
