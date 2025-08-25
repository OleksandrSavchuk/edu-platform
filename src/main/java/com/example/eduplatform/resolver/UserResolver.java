package com.example.eduplatform.resolver;

import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.dto.user.UserUpdateRequest;
import com.example.eduplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.canAccessUser(#id)")
    public UserResponse getUserById(@Argument Long id) {
        return userService.getById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserResponse updateUser(@Argument Long id, @Argument UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @MutationMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public Boolean deleteUser(@Argument Long id) {
        userService.deleteUser(id);
        return true;
    }

}
