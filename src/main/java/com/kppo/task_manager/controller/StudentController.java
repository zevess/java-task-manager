package com.kppo.task_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kppo.task_manager.dto.student.StudentCreateRequest;
import com.kppo.task_manager.dto.student.StudentResponse;
import com.kppo.task_manager.dto.student.StudentUpdateRequest;
import com.kppo.task_manager.service.impl.StudentServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Student", description = "API для получения, создания и редактирования студентов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentServiceImpl studentService;

    @Operation(summary = "Получение всех студентов", description = "Получение всех созданных студентов")
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAll());
    }

    @Operation(summary = "Получение студента", description = "Получение одного студента. Передаётся id студента")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getById(id));
    }

    @Operation(summary = "Создание студента", description = "Создание студента. Передаётся имя студента и его группа")
    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody @Valid StudentCreateRequest student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(student));
    }

    @Operation(summary = "Изменение студента", description = "Изменение студента. Передаётся имя студента и поля которые нужно изменить")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id,
            @RequestBody @Valid StudentUpdateRequest student) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.update(id, student));
    }
}
