package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Application, String> {
    Page<Application> findAll(Specification<Application> specification, Pageable pageable);
}
