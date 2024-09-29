package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {
    @Query(value = "SELECT AVG(sc.score) FROM t_score sc " +
            "JOIN t_attendance a ON sc.attendance_id = a.id " +
            "JOIN m_student st ON a.student_id = st.id " +
            "JOIN m_subject sb ON a.subject_id = sb.id " +
            "WHERE st.id = :studentId " +
            "AND sb.id = :subjectId " +
            "AND EXTRACT(MONTH FROM a.date) = :month",
            nativeQuery = true)
    Double findAverageByStudentIdAndSubjectIdAndMonth(@Param("studentId") String studentId,
                                                      @Param("subjectId") String subjectId,
                                                      @Param("month") Integer month);
}
