package com.enigma.edunity.controller;

import com.enigma.edunity.dto.request.CreateApplicationRequest;
import com.enigma.edunity.dto.request.UpdateApplicationRequest;
import com.enigma.edunity.dto.request.UpdateStudentRequest;
import com.enigma.edunity.dto.response.*;
import com.enigma.edunity.service.ApplicationService;
import com.enigma.edunity.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final ApplicationService applicationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping({"/{id}", "/profiles"})
    public ResponseEntity<CommonResponse<StudentResponse>> getStudentById(
            @PathVariable(required = false) String id
    ) {
        StudentResponse student = studentService.getStudentById(id);
        CommonResponse<StudentResponse> response = CommonResponse
                .<StudentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch data of " + student.getName())
                .data(student)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<StudentResponse>>> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "city", required = false) String city
    ) {
        Page<StudentResponse> pagedStudents = studentService.getAllStudents(page, size, name, city);

        CommonResponse<List<StudentResponse>> response = CommonResponse
                .<List<StudentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch all students")
                .data(pagedStudents.getContent())
                .paging(PagingResponse.builder()
                        .totalPages(pagedStudents.getTotalPages())
                        .totalElements(pagedStudents.getTotalElements())
                        .page(pagedStudents.getNumber())
                        .size(pagedStudents.getSize())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping(path = "/profiles")
    public ResponseEntity<CommonResponse<UpdateStudentResponse>> updateStudent(
            @RequestBody UpdateStudentRequest request
    ) {
        UpdateStudentResponse student = studentService.updateStudent(request);
        CommonResponse<UpdateStudentResponse> response = CommonResponse
                .<UpdateStudentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully update data of " + student.getName())
                .data(student)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<?>> deleteStudentById(
            @PathVariable String id
    ) {
        StudentResponse student = studentService.getStudentById(id);
        String studentName = student.getName();

        studentService.deleteStudent(id);
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete data of " + studentName)
                .data(student)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(path = "/applications")
    public ResponseEntity<CommonResponse<ApplicationResponse>> createApplication(
            @RequestBody CreateApplicationRequest request
    ) {
        ApplicationResponse application = applicationService.createApplication(request);
        CommonResponse<ApplicationResponse> response = CommonResponse
                .<ApplicationResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create application for subject " +
                        application.getSubject().getSubjectName() +
                        " on " + application.getDay() + " at " + application.getTime())
                .data(application)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(path = "/applications")
    public ResponseEntity<CommonResponse<List<ApplicationResponse>>> getAllStudentApplications(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "day", required = false) String day,
            @RequestParam(name = "time", required = false) String time
    ) {
        Page<ApplicationResponse> pagedApplications = applicationService.getAllStudentApplications(
                page, size, subject, day, time);

        CommonResponse<List<ApplicationResponse>> response = CommonResponse
                .<List<ApplicationResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch all applications")
                .data(pagedApplications.getContent())
                .paging(PagingResponse.builder()
                        .totalPages(pagedApplications.getTotalPages())
                        .totalElements(pagedApplications.getTotalElements())
                        .page(pagedApplications.getNumber())
                        .size(pagedApplications.getSize())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping(path = "/applications")
    public ResponseEntity<CommonResponse<ApplicationResponse>> updateApplication(
            @RequestBody UpdateApplicationRequest request
    ) {
        System.out.println("Request ID : " + request.getId());
        ApplicationResponse initialApplication = applicationService.getApplicationById(request.getId());
        ApplicationResponse application = applicationService.updateApplication(request);
        CommonResponse<ApplicationResponse> response = CommonResponse
                .<ApplicationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully update application for subject " + initialApplication.getSubject().getSubjectName() +
                        " on " + initialApplication.getDay() + " at " + initialApplication.getTime())
                .data(application)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
