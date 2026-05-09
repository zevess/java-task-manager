package com.kppo.task_manager.dto.timeEntry;

import jakarta.validation.constraints.NotNull;

public record TimeStopRequest(
        @NotNull(message = "Введите id записи времени") Long timeEntryId) {
}
