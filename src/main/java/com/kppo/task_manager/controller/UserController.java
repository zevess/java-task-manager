package com.kppo.task_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kppo.task_manager.dto.user.UserCreateRequest;
import com.kppo.task_manager.dto.user.UserDto;
import com.kppo.task_manager.dto.user.UserResponse;
import com.kppo.task_manager.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "API для получения, создания и редактирования пользователей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "Получение всех пользователей", description = "Получение всех пользователей")
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() {
        return userService.getUsers();
    }

    @Operation(summary = "Создание пользователя", description = "Создание пользователя. Передаётся логин и пароль")
    @PostMapping()
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserCreateRequest userDto) {
        return userService.create(userDto);
    }

    @Operation(summary = "Получение пользователя", description = "Получение пользователя по id. Передаётся id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Получение пользователя", description = "Получение пользователя по username. Передаётся username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return userService.getUser(username);
    }

}
