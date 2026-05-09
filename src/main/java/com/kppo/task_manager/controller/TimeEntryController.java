package com.kppo.task_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kppo.task_manager.dto.timeEntry.TimeEntryResponse;
import com.kppo.task_manager.dto.timeEntry.TimeStartRequest;
import com.kppo.task_manager.dto.timeEntry.TimeStopRequest;
import com.kppo.task_manager.service.impl.TimeEntryServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/time-entries")
public class TimeEntryController {
    private final TimeEntryServiceImpl timeEntryService;

    @PostMapping("/start")
    public ResponseEntity<TimeEntryResponse> start(@RequestBody @Valid TimeStartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryService.start(request));
    }

    @PostMapping("/stop")
    public ResponseEntity<TimeEntryResponse> stop(@RequestBody @Valid TimeStopRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryService.stop(request));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<TimeEntryResponse>> getByStudent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryService.getByStudent(id));
    }

}
