package com.kppo.task_manager.dto.user;

import java.util.Set;

public record UserLoggedDto(
        String username,
        String role,
        Set<String> permissions) {
}
