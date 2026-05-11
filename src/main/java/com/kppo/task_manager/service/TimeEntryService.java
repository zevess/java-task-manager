package com.kppo.task_manager.service;

import java.time.LocalDate;
import java.util.List;

import com.kppo.task_manager.dto.stats.WeeklyStatsResponse;
import com.kppo.task_manager.dto.timeEntry.TimeEntryResponse;
import com.kppo.task_manager.dto.timeEntry.TimeStartRequest;
import com.kppo.task_manager.dto.timeEntry.TimeStopRequest;

public interface TimeEntryService {
    TimeEntryResponse start(TimeStartRequest request);

    TimeEntryResponse stop(TimeStopRequest request);

    List<TimeEntryResponse> getByStudent(Long studentId);

    WeeklyStatsResponse getWeeklyStats(Long studentId, LocalDate startDate, LocalDate endDate,
            boolean includeNotBillable);
}
