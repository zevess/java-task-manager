package com.kppo.task_manager.mapper;

import java.util.stream.Collectors;

import com.kppo.task_manager.dto.user.UserCreateRequest;
import com.kppo.task_manager.dto.user.UserDto;
import com.kppo.task_manager.dto.user.UserLoggedDto;
import com.kppo.task_manager.dto.user.UserResponse;
import com.kppo.task_manager.model.Permission;
import com.kppo.task_manager.model.User;

public class UserMapper {
    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthority(),
                user.getRole().getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet()));
    }

    public static User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        return user;
    }

    public static User userRequestToUser(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.username());
        // user.setPassword(request.password());
        // user.setRole(request.role());
        return user;
    }

    public static UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().getAuthority());
    }

    public static UserLoggedDto userToUserLoggedDto(User user) {
        return new UserLoggedDto(
                user.getUsername(),
                user.getRole().getAuthority(),
                user.getRole().getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet()));
    }
}
