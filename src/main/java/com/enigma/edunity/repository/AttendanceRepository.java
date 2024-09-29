package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    @Query(value = "SELECT a.* FROM t_attendance a " +
            "WHERE student_id = :studentId " +
            "AND subject_id = :subjectId " +
            "AND EXTRACT(MONTH FROM date) = :month",
            nativeQuery = true)
    List<Attendance> findAllByStudentIdAndSubjectIdAndMonth(@Param("studentId") String studentId,
                                                            @Param("subjectId") String subjectId,
                                                            @Param("month") Integer month);
}
