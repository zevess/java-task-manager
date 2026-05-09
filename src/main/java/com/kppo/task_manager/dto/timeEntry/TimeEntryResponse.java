package com.kppo.task_manager.dto.timeEntry;

import java.time.LocalDateTime;

import com.kppo.task_manager.model.TimeEntry;

public record TimeEntryResponse(
        Long id,
        Long studentId,
        String studentName,
        String studentGroup,
        String taskType,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isBillable) {
    public static TimeEntryResponse from(TimeEntry entry) {
        return new TimeEntryResponse(
                entry.getId(),
                entry.getStudent().getId(),
                entry.getStudent().getName(),
                entry.getStudent().getGroupName(),
                entry.getTaskType().toString(),
                entry.getDescription(),
                entry.getStartTime(),
                entry.getEndTime(),
                entry.isBillable());
    }
}
