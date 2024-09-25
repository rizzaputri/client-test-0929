package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.Day;
import com.enigma.edunity.dto.request.AcceptApplicationRequest;
import com.enigma.edunity.dto.request.CreateApplicationRequest;
import com.enigma.edunity.dto.request.UpdateApplicationRequest;
import com.enigma.edunity.dto.response.ApplicationResponse;
import com.enigma.edunity.dto.response.StudentResponse;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.dto.response.TutorResponse;
import com.enigma.edunity.entity.Application;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.entity.Tutor;
import com.enigma.edunity.repository.ApplicationRepository;
import com.enigma.edunity.service.*;
import com.enigma.edunity.specification.ApplicationSpecification;
import com.enigma.edunity.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final StudentService studentService;
    private final TutorService tutorService;
    private final SubjectService subjectService;
    private final UserService userService;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplicationResponse createApplication(CreateApplicationRequest request) {
        validationUtil.validate(request);
        Student student = studentService.getByUsername();

        Application application = Application.builder()
                .day(Day.valueOf(request.getDay()))
                .time(LocalTime.parse(request.getTime()))
                .status(false)
                .student(student)
                .subject(subjectService.getById(request.getSubjectId()))
                .build();
        applicationRepository.saveAndFlush(application);
        return ResponseBuilder(application);
    }

    @Transactional(readOnly = true)
    @Override
    public Application getById(String id) {
        return applicationRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ApplicationResponse getApplicationById(String id) {
        System.out.println("===" + id + "===");
        List<String> roles = userService.getByContext().getAuthorities().stream()
                .map(Object::toString).toList();
        roles.forEach(System.out::println);
        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("Condition : " + roles.contains("ROLE_ADMIN"));
            Application application = getById(id);
            System.out.println("App ID: " + application.getId());
            return ResponseBuilder(application);
        } else if (roles.contains("ROLE_STUDENT")) {
            System.out.println("Condition : " + roles.contains("ROLE_STUDENT"));
            List<Application> applications = applicationRepository.findByStudentId(
                    studentService.getByUsername().getId()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Request not found"))
                    .stream().toList();
            List<String> applicationIds = applications.stream().map(Application::getId).toList();
            if (!applicationIds.contains(id)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You don't have access to get this request");
            }
            Application application = getById(id);
            System.out.println("App ID: " + application.getId());
            return ResponseBuilder(application);
        } else if (roles.contains("ROLE_TUTOR")) {
            System.out.println("Condition : " + roles.contains("ROLE_TUTOR"));
            List<Application> applications = applicationRepository.findByTutorId(
                            tutorService.getByUsername().getId()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Request not found"))
                    .stream().toList();
            List<String> applicationIds = applications.stream().map(Application::getId).toList();
            if (!applicationIds.contains(id)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You don't have access to get this request");
            }
            Application application = getById(id);
            System.out.println("App ID: " + application.getId());
            return ResponseBuilder(application);
        } else {
            System.out.println("=== No authorized roles found ===");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have access to get this request");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApplicationResponse> getAllApplications(
            Integer page, Integer size, String student, String subject, String day, String time
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Application> specification = Specification.where(null);
        Page<Application> requests;
        if (student != null && subject != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndSubjectAndDayAndTime(student, subject,
                                    enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndSubjectAndDay(student, subject, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndSubjectAndTime(student, subject, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndSubject(student, subject));
            }
        }
        if (student != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndDayAndTime(student, enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndDay(student, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndTime(student, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasStudent(student));
            }
        }
        if (subject != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasSubjectAndDayAndTime(subject, enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasSubjectAndDay(subject, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasSubjectAndTime(subject, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasSubject(subject));
            }
        }
        if (day != null) {
            Day enumDay = Day.valueOf(day.toUpperCase());
            if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasDayAndTime(enumDay, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasDay(enumDay));
            }
        }
        if (time != null) {
            LocalTime localTime = LocalTime.parse(time);
            specification = specification.and(ApplicationSpecification
                    .hasTime(localTime));
        }
        requests = applicationRepository.findAll(specification, pageable);
        return requests.map(this::ResponseBuilder);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApplicationResponse> getAllStudentApplications(
            Integer page, Integer size, String subject, String day, String time
    ) {
        Student student = studentService.getByUsername();
        Pageable pageable = PageRequest.of(page, size);
        Specification<Application> specification = Specification.where(ApplicationSpecification
                .hasStudent(student.getName()));
        Page<Application> requests;
        if (subject != null && day != null) {
            Day enumDay = Day.valueOf(day.toUpperCase());
            if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasSubjectAndDayAndTime(subject, enumDay, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasSubjectAndDay(subject, enumDay));
            }
        }
        if (subject != null) {
            if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasSubjectAndTime(subject, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasSubject(subject));
            }
        }
        if (day != null) {
            Day enumDay = Day.valueOf(day.toUpperCase());
            if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasDayAndTime(enumDay, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasDay(enumDay));
            }
        }
        if (time != null) {
            LocalTime localTime = LocalTime.parse(time);
            specification = specification.and(ApplicationSpecification
                    .hasTime(localTime));
        }
        requests = applicationRepository.findAll(specification, pageable);
        return requests.map(this::ResponseBuilder);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApplicationResponse> getAllTutorApplications(
            Integer page, Integer size, String student, String subject, String day, String time
    ) {
        Tutor tutor = tutorService.getByUsername();
        Pageable pageable = PageRequest.of(page, size);
        Specification<Application> specification = Specification.where(ApplicationSpecification
                .hasTutor(tutor.getId()));
        Page<Application> requests;
        if (student != null && subject != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndSubjectAndDayAndTime(
                                    student, subject, enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndSubjectAndDay(student, subject, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndSubjectAndTime(student, subject, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndSubject(student, subject));
            }
        }
        if (student != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndDayAndTime(student, enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasStudentAndDay(student, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasStudentAndTime(student, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasStudent(student));
            }
        }
        if (subject != null) {
            if (day != null) {
                Day enumDay = Day.valueOf(day.toUpperCase());
                if (time != null) {
                    LocalTime localTime = LocalTime.parse(time);
                    specification = specification.and(ApplicationSpecification
                            .hasSubjectAndDayAndTime(subject, enumDay, localTime));
                } else {
                    specification = specification.and(ApplicationSpecification
                            .hasSubjectAndDay(subject, enumDay));
                }
            } else if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasSubjectAndTime(subject, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasSubject(subject));
            }
        }
        if (day != null) {
            Day enumDay = Day.valueOf(day.toUpperCase());
            if (time != null) {
                LocalTime localTime = LocalTime.parse(time);
                specification = specification.and(ApplicationSpecification
                        .hasDayAndTime(enumDay, localTime));
            } else {
                specification = specification.and(ApplicationSpecification
                        .hasDay(enumDay));
            }
        }
        if (time != null) {
            LocalTime localTime = LocalTime.parse(time);
            specification = specification.and(ApplicationSpecification
                    .hasTime(localTime));
        }
        requests = applicationRepository.findAll(specification, pageable);
        return requests.map(this::ResponseBuilder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplicationResponse updateApplication(UpdateApplicationRequest request) {
        System.out.println("Request in : " + request.getId());
        validationUtil.validate(request);
        System.out.println("request validated : " + request.getId());
        List<Application> applications = applicationRepository.findByStudentId(
                studentService.getByUsername().getId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Request not found"))
                .stream().toList();
        applications.forEach(System.out::println);
        List<String> applicationIds = applications.stream().map(Application::getId).toList();
        applicationIds.forEach(System.out::println);
        if (!applicationIds.contains(request.getId())) {
            System.out.println("Condition : " + !applicationIds.contains(request.getId()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have access to update this request");
        }

        Application application = getById(request.getId());
        System.out.println("App ID : " + application.getId());
        if (request.getSubjectId() != null) {
            application.setSubject(subjectService.getById(request.getSubjectId()));
        }
        if (request.getDay() != null) {
            application.setDay(Day.valueOf(request.getDay()));
        }
        if (request.getTime() != null) {
            application.setTime(LocalTime.parse(request.getTime()));
        }

        return ResponseBuilder(application);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplicationResponse acceptApplication(AcceptApplicationRequest request) {
        Application application = getById(request.getApplicationId());
        application.setStatus(true);

        Tutor tutor = tutorService.getByUsername();
        application.setTutor(tutor);
        return ResponseBuilder(application);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }

    private ApplicationResponse ResponseBuilder(Application application) {
        String tutorId;
        if (application.getTutor() == null) {
            tutorId = null;
        } else {
            tutorId = application.getTutor().getId();
        }
        return ApplicationResponse.builder()
                .applicationId(application.getId())
                .student(StudentResponse.builder()
                        .studentId(application.getStudent().getId())
                        .name(application.getStudent().getName())
                        .location(application.getStudent().getLocation())
                        .build())
                .subject(SubjectResponse.builder()
                        .subjectId(application.getSubject().getId())
                        .subjectName(application.getSubject().getSubjectName())
                        .build())
                .tutorId(tutorId)
                .day(application.getDay())
                .time(application.getTime())
                .status(application.getStatus())
                .build();
    }
}
