package com.enigma.edunity.repository;

import com.enigma.edunity.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query(value = "SELECT s.email FROM m_student s WHERE email = :email",
            nativeQuery = true)
    String findEmail(String email);

    @Query(value = "SELECT s.phone_number FROM m_student s WHERE phone_number = :phoneNumber",
            nativeQuery = true)
    String findPhoneNumber(String phoneNumber);
}
