package com.kppo.task_manager.dto.stats;

import java.time.LocalDate;
import java.util.List;

public record WeeklyStatsResponse(
                Long studentId,
                String studentName,
                LocalDate weekStart,
                LocalDate weekEnd,
                double totalHours,
                List<DayStats> days) {
}
