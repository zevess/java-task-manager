package com.kppo.task_manager.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kppo.task_manager.dto.student.StudentCreateRequest;
import com.kppo.task_manager.dto.student.StudentResponse;
import com.kppo.task_manager.dto.student.StudentUpdateRequest;
import com.kppo.task_manager.model.Student;
import com.kppo.task_manager.repository.StudentRepository;
import com.kppo.task_manager.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> getAll(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentResponse::from).toList();
    }
    @Override
    public StudentResponse getById(Long studentId){    
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
        return StudentResponse.from(student);
    }
    @Override
    public StudentResponse create(StudentCreateRequest request){
        Student student = Student.builder()
                        .name(request.name())
                        .groupName(request.groupName())
                        .build();
        Student saved = studentRepository.save(student);
        return StudentResponse.from(saved);
    }
    @Override
    public StudentResponse update(Long studentId, StudentUpdateRequest request){
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
        if(request.name() != null){
            student.setName(request.name());
        }
        if(request.groupName() != null){
            student.setGroupName(request.groupName());
        }
        return StudentResponse.from(studentRepository.save(student));
    }
}
