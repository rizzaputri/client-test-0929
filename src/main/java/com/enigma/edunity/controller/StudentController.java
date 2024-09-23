package com.enigma.edunity.controller;

import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.dto.response.PagingResponse;
import com.enigma.edunity.dto.response.StudentResponse;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<StudentResponse>> getStudentById(
            @PathVariable String id
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
}
