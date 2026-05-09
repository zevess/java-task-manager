package com.kppo.task_manager.service;

import java.util.List;

import com.kppo.task_manager.dto.timeEntry.TimeEntryResponse;
import com.kppo.task_manager.dto.timeEntry.TimeStartRequest;
import com.kppo.task_manager.dto.timeEntry.TimeStopRequest;

public interface TimeEntryService {
    TimeEntryResponse start(TimeStartRequest request);

    TimeEntryResponse stop(TimeStopRequest request);

    List<TimeEntryResponse> getByStudent(Long studentId);
}
