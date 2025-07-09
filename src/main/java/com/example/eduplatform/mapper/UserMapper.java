package com.example.eduplatform.mapper;

import com.example.eduplatform.dto.user.UserRegisterRequest;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterRequest userRegisterRequest);

    UserResponse toDto(User user);

    List<UserResponse> toDto(List<User> users);

    void updateEntityFromDto(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

}
