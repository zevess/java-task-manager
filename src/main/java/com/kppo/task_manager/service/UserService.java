package com.kppo.task_manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kppo.task_manager.dto.user.UserCreateRequest;
import com.kppo.task_manager.dto.user.UserDto;
import com.kppo.task_manager.dto.user.UserResponse;

public interface UserService {
    ResponseEntity<List<UserResponse>> getUsers();

    ResponseEntity<UserResponse> create(UserCreateRequest userDto);

    ResponseEntity<UserResponse> getUser(Long userId);

    ResponseEntity<UserResponse> getUser(String username);

    ResponseEntity<UserResponse> updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);
}
