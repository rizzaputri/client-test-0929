package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.request.RegisterStudentRequest;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.repository.StudentRepository;
import com.enigma.edunity.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerStudent(Student student) {
        studentRepository.saveAndFlush(student);
    }

    @Transactional(readOnly = true)
    @Override
    public String findStudentEmail(String email) {
        return studentRepository.findEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public String findStudentPhoneNumber(String phoneNumber) {
        return studentRepository.findPhoneNumber(phoneNumber);
    }
}
