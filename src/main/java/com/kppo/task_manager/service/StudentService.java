package com.kppo.task_manager.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kppo.task_manager.dto.StudentCreateRequest;
import com.kppo.task_manager.dto.StudentResponse;
import com.kppo.task_manager.dto.StudentUpdateRequest;
import com.kppo.task_manager.model.Student;
import com.kppo.task_manager.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<StudentResponse> getAll(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentResponse::from).toList();
    }

    public StudentResponse getById(Long id){    
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
        return StudentResponse.from(student);
    }

    public StudentResponse create(StudentCreateRequest request){
        Student student = Student.builder()
                        .name(request.name())
                        .groupName(request.groupName())
                        .build();
        Student saved = studentRepository.save(student);
        return StudentResponse.from(saved);
    }

    public StudentResponse update(Long id, StudentUpdateRequest request){
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
        if(request.name() != null){
            student.setName(request.name());
        }
        if(request.groupName() != null){
            student.setGroupName(request.groupName());
        }
        return StudentResponse.from(studentRepository.save(student));
    }
}
