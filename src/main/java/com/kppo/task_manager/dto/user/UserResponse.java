package com.kppo.task_manager.dto.user;

public record UserResponse(
        Long id,
        String username,
        String role) {
}
