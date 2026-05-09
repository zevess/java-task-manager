package com.kppo.task_manager.service;

import java.util.List;

import com.kppo.task_manager.dto.student.StudentCreateRequest;
import com.kppo.task_manager.dto.student.StudentResponse;
import com.kppo.task_manager.dto.student.StudentUpdateRequest;

public interface StudentService{
    List<StudentResponse> getAll();

    StudentResponse getById(Long studentId);

    StudentResponse create(StudentCreateRequest request);

    StudentResponse update(Long studentId, StudentUpdateRequest request);
}