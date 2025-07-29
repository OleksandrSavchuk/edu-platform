package com.example.eduplatform.service;

import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.entity.User;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse createUser(UserRegisterRequest userRegisterRequest);

    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    UserResponse getUserResponseByEmail(String email);

    User getUserByEmail(String email);

    User getUserById(Long id);

    boolean existsByEmail(String email);

    void deleteUser(Long id);

}
