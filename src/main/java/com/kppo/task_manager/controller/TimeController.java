package com.kppo.task_manager.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kppo.task_manager.dto.stats.WeeklyStatsResponse;
import com.kppo.task_manager.dto.timeEntry.TimeEntryResponse;
import com.kppo.task_manager.dto.timeEntry.TimeStartRequest;
import com.kppo.task_manager.dto.timeEntry.TimeStopRequest;
import com.kppo.task_manager.service.impl.TimeEntryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Time", description = "API для создания сессии учебной задачи и их учёта")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/time")
public class TimeController {
    private final TimeEntryServiceImpl timeEntryService;

    @Operation(summary = "Старт сессии задачи", description = "Создание сессии учебной задачи студента. Передаётся id студента, тип задачи, описание задачи")
    @PostMapping("/start")
    public ResponseEntity<TimeEntryResponse> start(@RequestBody @Valid TimeStartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryService.start(request));
    }

    @Operation(summary = "Завершение сессии задачи", description = "Завершение выполнения сессии учебной задачи. Передаётся id задачи")
    @PostMapping("/stop")
    public ResponseEntity<TimeEntryResponse> stop(@RequestBody @Valid TimeStopRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryService.stop(request));
    }

    @Operation(summary = "Сессии задач студента", description = "Получение сессии задач студента. Передаётся id студента")
    @GetMapping("/student/{id}")
    public ResponseEntity<List<TimeEntryResponse>> getByStudent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryService.getByStudent(id));
    }

    @Operation(summary = "Статистика по неделе", description = "Получение статистики студента по завершенным сессиям задач. Передаётся id студента, опциональные параметры: даты начала и конца недели")
    @GetMapping("/weekly")
    public ResponseEntity<WeeklyStatsResponse> getWeeklyStats(
            @RequestParam Long studentId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) boolean includeNotBillable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(timeEntryService.getWeeklyStats(studentId, startDate, endDate, includeNotBillable));
    }
}
