package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.City;
import com.enigma.edunity.constant.Province;
import com.enigma.edunity.dto.request.UpdateStudentRequest;
import com.enigma.edunity.dto.response.StudentResponse;
import com.enigma.edunity.dto.response.UpdateStudentResponse;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.repository.StudentRepository;
import com.enigma.edunity.repository.UserAccountRepository;
import com.enigma.edunity.service.StudentService;
import com.enigma.edunity.service.UserService;
import com.enigma.edunity.specification.StudentSpecification;
import com.enigma.edunity.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final UserService userService;

    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

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
        List<String> roles = userService.getByContext().getAuthorities().stream()
                .map(Object::toString).toList();
        if (roles.contains("ROLE_ADMIN")) {
            Student student = getById(id);
            return ResponseBuilder(student);
        } else if (roles.contains("ROLE_STUDENT")) {
            Student student = getByUsername();
            return ResponseBuilder(student);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have permission to access this resource");
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

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateStudentResponse updateStudent(UpdateStudentRequest request) {
        validationUtil.validate(request);
        Student student = getByUsername();

        if (request.getUsername() != null) {
            if (userService.findByUsername(student.getUserAccount().getUsername()) != null) {
                throw new IllegalArgumentException("Username already exist");
            }
            student.getUserAccount().setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            String hashPassword = passwordEncoder.encode(request.getPassword());
            student.getUserAccount().setPassword(hashPassword);
        }
        if (request.getName() != null) {
            student.setName(request.getName());
        }
        if (request.getEmail() != null) {
            student.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            student.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getLocation() != null) {
            student.getLocation().setCity(City.valueOf(request.getLocation().getCity()));
            student.getLocation().setProvince(Province.valueOf(request.getLocation().getProvince()));
        }

        return UpdateStudentResponse.builder()
                .username(student.getUserAccount().getUsername())
                .name(student.getName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .location(student.getLocation())
                .build();
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
