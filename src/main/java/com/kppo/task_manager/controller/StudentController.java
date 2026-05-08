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

import com.kppo.task_manager.dto.StudentCreateRequest;
import com.kppo.task_manager.dto.StudentResponse;
import com.kppo.task_manager.dto.StudentUpdateRequest;
import com.kppo.task_manager.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getById(id));
    }
    
    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody @Valid StudentCreateRequest student){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(student));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody @Valid StudentUpdateRequest student){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.update(id, student));
    }
}
