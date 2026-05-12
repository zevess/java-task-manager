package com.kppo.task_manager.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kppo.task_manager.dto.user.UserCreateRequest;
import com.kppo.task_manager.dto.user.UserDto;
import com.kppo.task_manager.dto.user.UserResponse;
import com.kppo.task_manager.exception.AppException;
import com.kppo.task_manager.exception.ResourceNotFoundException;
import com.kppo.task_manager.mapper.UserMapper;
import com.kppo.task_manager.model.Role;
import com.kppo.task_manager.model.User;
import com.kppo.task_manager.repository.RoleRepository;
import com.kppo.task_manager.repository.UserRepository;
import com.kppo.task_manager.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userRepository.findAll().stream().map(UserMapper::userToUserResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public ResponseEntity<UserResponse> create(UserCreateRequest userDto) {
        User user = UserMapper.userRequestToUser(userDto);

        Role role = roleRepository.findByName(userDto.role()).orElseThrow(
                () -> new ResourceNotFoundException("Роль не найдена"));

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.userToUserResponse(savedUser));
    }

    @Override
    public ResponseEntity<UserResponse> getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.userToUserResponse(user));
    }

    @Override
    public ResponseEntity<UserResponse> getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.userToUserResponse(user));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        Role role = roleRepository.findByName(userDto.role()).orElseThrow(
                () -> new ResourceNotFoundException("Роль не найдена"));

        user.setUsername(userDto.username());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDto.password()));

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.userToUserResponse(savedUser));
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        userRepository.delete(user);

        return String.format("Пользователь удалён", userId);
    }
}
