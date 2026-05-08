package com.kppo.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kppo.task_manager.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}
