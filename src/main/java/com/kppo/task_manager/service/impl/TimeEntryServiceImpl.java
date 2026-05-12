package com.kppo.task_manager.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kppo.task_manager.dto.stats.DayStats;
import com.kppo.task_manager.dto.stats.WeeklyStatsResponse;
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

        TaskType type;
        try {
            type = TaskType.valueOf(request.taskType().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип задачи");
        }

        TimeEntry entry = TimeEntry.builder()
                .student(student)
                .taskType(type)
                .description(request.description())
                .startTime(LocalDateTime.now())
                .isBillable(request.billable())
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
        if (timeEntries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сессии не найдены");
        }
        return timeEntries.stream().map(TimeEntryResponse::from).toList();
    }

    @Override
    public WeeklyStatsResponse getWeeklyStats(Long studentId, LocalDate startDate, LocalDate endDate,
            boolean includeNotBillable) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студен не найден"));
        LocalDateTime start = startDate != null ? startDate.atStartOfDay()
                : LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime end = endDate != null ? endDate.plusDays(1).atStartOfDay() : start.plusDays(7);

        List<TimeEntry> entries = timeEntryRepository.findCompletedByStudentAndDateRange(studentId,
                start, end, includeNotBillable);

        Map<LocalDate, List<TimeEntry>> groupedByDate = entries.stream()
                .collect(Collectors.groupingBy(e -> e.getStartTime().toLocalDate(), TreeMap::new, Collectors.toList()));
        List<DayStats> daysStats = new ArrayList<>();
        double totalMinutes = 0;

        for (Map.Entry<LocalDate, List<TimeEntry>> dayEntry : groupedByDate.entrySet()) {
            LocalDate date = dayEntry.getKey();
            double dayMinutes = 0;
            Map<String, Double> typeHours = new LinkedHashMap<>();

            for (TimeEntry entry : dayEntry.getValue()) {
                long mins = Duration.between(entry.getStartTime(), entry.getEndTime()).toMinutes();
                dayMinutes += mins;

                String type = entry.getTaskType().name();
                typeHours.merge(type, mins / 60.0, Double::sum);
            }
            totalMinutes += dayMinutes;
            daysStats.add(new DayStats(
                    date,
                    date.getDayOfWeek().toString(),
                    dayMinutes / 60,
                    typeHours));
        }

        return new WeeklyStatsResponse(
                studentId,
                student.getName(),
                start.toLocalDate(),
                end.minusDays(1).toLocalDate(),
                totalMinutes / 60,
                daysStats);
    }
}
