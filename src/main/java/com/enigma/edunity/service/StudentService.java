package com.enigma.edunity.service;

import com.enigma.edunity.entity.Student;

public interface StudentService {
    void registerStudent(Student request);
    String findStudentEmail(String email);
    String findStudentPhoneNumber(String phoneNumber);
}
