package com.kppo.task_manager.dto.stats;

import java.time.LocalDate;
import java.util.Map;

public record DayStats(
                LocalDate date,
                String dayName,
                double hours,
                Map<String, Double> hoursByTaskType) {
}
