package com.kppo.task_manager.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kppo.task_manager.dto.timeEntry.TimeEntryResponse;
import com.kppo.task_manager.dto.timeEntry.TimeStartRequest;
import com.kppo.task_manager.dto.timeEntry.TimeStopRequest;
import com.kppo.task_manager.enums.TaskType;
import com.kppo.task_manager.model.Student;
import com.kppo.task_manager.model.TimeEntry;
import com.kppo.task_manager.repository.StudentRepository;
import com.kppo.task_manager.repository.TimeEntryRepository;
import com.kppo.task_manager.service.TimeEntryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeEntryServiceImpl implements TimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final StudentRepository studentRepository;

    @Override
    public TimeEntryResponse start(TimeStartRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студен не найден"));

        if (timeEntryRepository.findByStudentIdAndEndTimeIsNull(student.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "У студента уже есть активная сессия задачи");
        }

        TaskType type = TaskType.valueOf(request.taskType().trim().toUpperCase());

        TimeEntry entry = TimeEntry.builder()
                .student(student)
                .taskType(type)
                .description(request.description())
                .startTime(LocalDateTime.now())
                .isBillable(Boolean.TRUE.equals(request.billable()))
                .build();

        return TimeEntryResponse.from(timeEntryRepository.save(entry));
    }

    @Override
    public TimeEntryResponse stop(TimeStopRequest request) {
        TimeEntry entry = timeEntryRepository.findById(request.timeEntryId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись сессии задачи не найдена"));

        if (entry.getEndTime() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Запись сессии задачи уже остановлена");
        }
        entry.setEndTime(LocalDateTime.now());
        return TimeEntryResponse.from(timeEntryRepository.save(entry));
    }

    @Override
    public List<TimeEntryResponse> getByStudent(Long studentId) {
        List<TimeEntry> timeEntries = timeEntryRepository.findByStudentId(studentId);
        return timeEntries.stream().map(TimeEntryResponse::from).toList();
    }
}
