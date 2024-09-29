package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findAllByStudentId(String studentId, Pageable pageable);
    List<Report> findAllByStudentIdAndMonth(String studentId, String month);

    Page<Report> findAllByStudentIdAndSubjectId(String studentId, String subjectId, Pageable pageable);
    Page<Report> findAllByStudentIdAndSubjectIdAndMonth(String studentId, String subjectId, String month, Pageable pageable);
}
