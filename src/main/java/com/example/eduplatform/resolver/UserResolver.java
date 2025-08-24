package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    @QueryMapping
    public UserResponse getUserById(@Argument Long id) {
        return userService.getById(id);
    }

    @QueryMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public UserResponse updateUser(@Argument Long id, @Argument UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @MutationMapping
    public void deleteUser(@Argument Long id) {
        userService.deleteUser(id);
    }

}
