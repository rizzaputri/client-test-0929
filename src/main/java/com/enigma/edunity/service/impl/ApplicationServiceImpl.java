package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.response.ApplicationResponse;
import com.enigma.edunity.dto.response.StudentResponse;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.entity.Application;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.repository.RequestRepository;
import com.enigma.edunity.service.ApplicationService;
import com.enigma.edunity.service.StudentService;
import com.enigma.edunity.service.UserService;
import com.enigma.edunity.specification.ApplicationSpecification;
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
public class ApplicationServiceImpl implements ApplicationService {
    private final RequestRepository requestRepository;
    private final StudentService studentService;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Application getById(String id) {
        return requestRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ApplicationResponse getRequestById(String id) {
        if (userService.getByContext().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Application application = getById(id);
            return ResponseBuilder(application);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApplicationResponse> getAllRequests(Integer page, Integer size,
                                                    String student, String subject,
                                                    String day, String time
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Application> specification = Specification.where(null);

        Page<Application> requests;
        if (student != null && subject != null && day != null) {
            if (time != null) {
                specification = specification.and(ApplicationSpecification.hasStudentAndSubjectAndDayAndTime(student, subject, day, time));
            } else {
                specification = specification.and(ApplicationSpecification.hasStudentAndSubjectAndDay(student, subject, day));
            }
        }
        if (student != null && day != null) {
            if (time != null) {
                specification = specification.and(ApplicationSpecification.hasStudentAndDayAndTime(student, day, time));
            } else {
                specification = specification.and(ApplicationSpecification.hasStudentAndDay(student, day));
            }
        }
        if (student != null) {
            if (subject != null) {
                specification = specification.and(ApplicationSpecification.hasStudentAndSubject(student, subject));
            } else if (time != null) {
                specification = specification.and(ApplicationSpecification.hasStudentAndTime(student, time));
            }
        }
        if (subject != null && time != null) {
            if (student != null) {
                specification = specification.and(ApplicationSpecification.hasStudentAndSubjectAndTime(student, subject, time));
            } else if (day != null) {
                specification = specification.and(ApplicationSpecification.hasSubjectAndDayAndTime(subject, day, time));
            }
        }
        if (subject != null) {
            if (day != null) {
                specification = specification.and(ApplicationSpecification.hasSubjectAndDay(subject, day));
            } else if (time != null) {
                specification = specification.and(ApplicationSpecification.hasSubjectAndTime(subject, time));
            }
        }
        if (day != null && time != null) {
            specification = specification.and(ApplicationSpecification.hasSubjectAndDayAndTime(subject, day, time));
        }

        requests = requestRepository.findAll(specification, pageable);
        return requests.map(this::ResponseBuilder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRequestById(String id) {
        requestRepository.deleteById(id);
    }

    private ApplicationResponse ResponseBuilder(Application application) {
        Student student = studentService.getByUsername();
        return ApplicationResponse.builder()
                .requestId(application.getId())
                .student(StudentResponse.builder()
                        .studentId(student.getId())
                        .name(student.getName())
                        .location(student.getLocation())
                        .build())
                .subject(SubjectResponse.builder()
                        .subjectId(application.getSubject().getId())
                        .subjectName(application.getSubject().getSubjectName())
                        .build())
                .day(application.getDay())
                .time(application.getTime())
                .build();
    }
}
