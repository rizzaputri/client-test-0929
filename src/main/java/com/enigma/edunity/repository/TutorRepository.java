package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, String> {
    @Query(value = "SELECT s.email FROM m_student s WHERE email = :email",
            nativeQuery = true)
    Optional<String> findEmail(String email);

    @Query(value = "SELECT s.phone_number FROM m_student s WHERE phone_number = :phoneNumber",
            nativeQuery = true)
    String findPhoneNumber(String phoneNumber);
}
