package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Page<Subject> findBySubjectNameLike(Pageable pageable, String name);
}
