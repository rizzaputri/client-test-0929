package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Page<Student> findAll(Specification<Student> specification, Pageable pageable);
    Optional<Student> findByUserAccountUsername(String username);
}
