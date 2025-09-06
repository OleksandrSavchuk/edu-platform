package com.example.eduplatform.service.impl;

import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.entity.User;
import com.example.eduplatform.exception.ResourceNotFoundException;
import com.example.eduplatform.mapper.UserMapper;
import com.example.eduplatform.repository.UserRepository;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final CacheManager cacheManager;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getById", key = "#id")
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "UserService::getById", key = "#result.id"),
                    @CachePut(value = "UserService::getByEmail", key = "#result.email"),
                    @CachePut(value = "UserService::getUserByEmail", key = "#result.email")
            }
    )
    public UserResponse createUser(UserRegisterRequest userRegisterRequest) {
        User user = userMapper.toEntity(userRegisterRequest);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "UserService::getById", key = "#result.id"),
                    @CachePut(value = "UserService::getByEmail", key = "#result.email"),
            }
    )
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userMapper.updateEntityFromDto(userUpdateRequest, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByEmail", key = "#email")
    public UserResponse getUserResponseByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::existsByEmail", key = "#email")
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        cacheManager.getCache("UserService::getByEmail").evict(user.getEmail());
        cacheManager.getCache("UserService::existsByEmail").evict(user.getEmail());
        userRepository.deleteById(id);
    }
}
