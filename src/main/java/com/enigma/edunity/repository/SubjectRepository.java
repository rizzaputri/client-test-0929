package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Page<Subject> findBySubjectNameLike(Pageable pageable, String name);
    @Query(value = "SELECT sb.* FROM m_subject sb " +
            "JOIN t_application ap ON ap.subject_id = sb.id " +
            "JOIN m_student st ON st.id = ap.student_id " +
            "WHERE ap.status = true " +
            "AND st.id = :id",
        nativeQuery = true)
    List<Subject> findAllByStudentId(@Param("id") String studentId);
}
