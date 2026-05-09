package com.kppo.task_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kppo.task_manager.model.TimeEntry;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    Optional<TimeEntry> findByStudentIdAndEndTimeIsNull(Long studentId);

    List<TimeEntry> findByStudentId(Long studentId);
}
