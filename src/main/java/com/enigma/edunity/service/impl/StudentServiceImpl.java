package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.response.StudentResponse;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.repository.StudentRepository;
import com.enigma.edunity.service.StudentService;
import com.enigma.edunity.service.UserService;
import com.enigma.edunity.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerStudent(Student student) {
        studentRepository.saveAndFlush(student);
    }

    @Transactional(readOnly = true)
    @Override
    public Student getById(String id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Student getByUsername() {
        String username = userService.getByContext().getUsername();
        return studentRepository.findByUserAccountUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public StudentResponse getStudentById(String id) {
        if (userService.getByContext().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Student student = getById(id);
            return ResponseBuilder(student);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<StudentResponse> getAllStudents(Integer page, Integer size, String name, String city) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Student> specification = Specification.where(null);

        Page<Student> students;
        if (name != null && city != null) {
            specification = specification.and(StudentSpecification.hasNameAndCity(name, city));
        } else if (name != null) {
            specification = specification.and(StudentSpecification.hasName(name));
        } else if (city != null) {
            specification = specification.and(StudentSpecification.hasCity(city));
        }
        students = studentRepository.findAll(specification, pageable);

        return students.map(this::ResponseBuilder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteStudent(String id) {
        String userAccountId = getById(id).getUserAccount().getId();
        studentRepository.deleteById(id);
        userService.deleteUserAccount(userAccountId);
    }

    private StudentResponse ResponseBuilder(Student student) {
        return StudentResponse.builder()
                .studentId(student.getId())
                .name(student.getName())
                .location(student.getLocation())
                .build();
    }
}
