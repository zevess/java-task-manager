package com.kppo.task_manager.dto.timeEntry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TimeStartRequest(
        @NotNull(message = "Введите id студента") Long studentId,

        @NotBlank(message = "Введите тип задачи") String taskType,

        String description,

        Boolean billable) {
}
