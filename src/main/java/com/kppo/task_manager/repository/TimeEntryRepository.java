package com.kppo.task_manager.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kppo.task_manager.model.TimeEntry;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
        Optional<TimeEntry> findByStudentIdAndEndTimeIsNull(Long studentId);

        List<TimeEntry> findByStudentId(Long studentId);

        @Query("SELECT t FROM TimeEntry t " +
                        "WHERE t.student.id = :studentId " +
                        "AND t.startTime BETWEEN :start AND :end " +
                        "AND t.endTime IS NOT NULL " +
                        "AND (:includeNotBillable = true OR t.isBillable = true)")
        List<TimeEntry> findCompletedByStudentAndDateRange(
                        @Param("studentId") Long studentId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("includeNotBillable") boolean includeNotBillable);
}
