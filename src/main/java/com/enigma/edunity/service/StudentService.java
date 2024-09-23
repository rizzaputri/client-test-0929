package com.enigma.edunity.service;

import com.enigma.edunity.dto.response.StudentResponse;
import com.enigma.edunity.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    void registerStudent(Student request);
    Student getById(String id);
    Student getByUsername();
    StudentResponse getStudentById(String id);
    Page<StudentResponse> getAllStudents(Integer page, Integer size,
                                         String name, String city);
    void deleteStudent(String id);
}
