package com.kppo.task_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentCreateRequest(
    @NotBlank(message = "Введите имя")
    String name,

    @NotBlank(message = "Введите номер группы")
    String groupName
){}
