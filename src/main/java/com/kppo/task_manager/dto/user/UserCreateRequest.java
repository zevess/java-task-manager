package com.kppo.task_manager.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @NotBlank(message = "Введите имя") String username,
        @NotBlank(message = "Введите пароль") String password,
        @NotBlank(message = "Введите роль") String role) {
}
