package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, String> {
    Page<Tutor> findAll(Specification<Tutor> specification, Pageable pageable);
    Optional<Tutor> findByUserAccountUsername(String username);
}
