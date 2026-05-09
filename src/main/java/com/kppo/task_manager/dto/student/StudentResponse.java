package com.kppo.task_manager.dto.student;

import com.kppo.task_manager.model.Student;

public record StudentResponse(
        Long id,
        String name,
        String groupName) {
    public static StudentResponse from(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getGroupName());
    }
}
